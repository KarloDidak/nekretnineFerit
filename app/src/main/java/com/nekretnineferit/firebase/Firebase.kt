package com.nekretnineferit.firebase

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.nekretnineferit.NekretnineFeritApp
import com.nekretnineferit.R
import com.nekretnineferit.data.House
import com.nekretnineferit.firebase.MESSAGE_TYPE.*
import com.nekretnineferit.utils.Constants.CONTENT_SCHEME
import com.nekretnineferit.utils.Constants.USERS_COLLECTION
import com.nekretnineferit.utils.Constants.HOUSES_COLLECTION
import com.nekretnineferit.utils.Constants.HOUSE_ID_FIELD
import com.nekretnineferit.utils.Constants.IMAGES_PATH
import com.nekretnineferit.utils.validateUserInputs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.UUID

private val _firebaseFlowUser = MutableSharedFlow<FlowMessage<FirebaseUser>>()
val firebaseFlowUser = _firebaseFlowUser.asSharedFlow()

private val _firebaseFlowHouses = MutableSharedFlow<FlowMessage<List<House>>>()
val firebaseFlowHouses = _firebaseFlowHouses.asSharedFlow()

fun userGetCurrent(): FirebaseUser? {
    return FirebaseAuth.getInstance().currentUser
}

fun userLogout(){
    FirebaseAuth.getInstance().signOut()
}

fun userLogin(coroutineScope: CoroutineScope, email: String, password: String) {

    emitUserMessageLoading(coroutineScope)

    val checkInputs = validateUserInputs(email, password)
    if (checkInputs.type != VALIDATION_OK) {
        emitUserMessage(coroutineScope, checkInputs.type, checkInputs.message)
        return
    }

    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnSuccessListener {
            emitUserMessageSuccess(coroutineScope, it.user!!)
        }
        .addOnFailureListener {
            emitUserMessageError(coroutineScope, it)
        }
}

fun userRegister(coroutineScope: CoroutineScope, name: String, email: String, password: String) {
    emitUserMessageLoading(coroutineScope)

    val checkInputs = validateUserInputs(email, password)
    if (checkInputs.type != VALIDATION_OK) {
        emitMessage(coroutineScope, _firebaseFlowUser, checkInputs.type, checkInputs.message)
        return
    }

    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener {
            userUpdate(coroutineScope, name, email, "")
        }.addOnFailureListener {
            emitUserMessageError(coroutineScope, it)
        }
}

fun userUpdate(coroutineScope: CoroutineScope, name: String, email: String, imagePath: String) {
    val currUser = userGetCurrent() ?: return

    emitUserMessageLoading(coroutineScope)

    coroutineScope.launch {
        val profileUpdates = userProfileChangeRequest {
            this.displayName = name
            if (imagePath != "") {
                var imagePathNew = listOf<String>()
                async {
                    imagePathNew = uploadFilesToFirebaseStorage(listOf(imagePath),
                        "$USERS_COLLECTION/$IMAGES_PATH/")
                }.await()
                this.photoUri = Uri.parse(imagePathNew[0])
            }
        }

        currUser.updateProfile(profileUpdates)
            .addOnSuccessListener {
                currUser.updateEmail(email)
                    .addOnSuccessListener {
                        emitUserMessageSuccess(coroutineScope, currUser)
                    }.addOnFailureListener {
                        emitUserMessageError(coroutineScope, it)
                    }
            }.addOnFailureListener {
                emitUserMessageError(coroutineScope, it)
            }
    }
}

suspend fun uploadFilesToFirebaseStorage(
    files: List<String>,
    collection: String
): List<String> {
    val filesRes = mutableListOf<String>()

    val tasks = mutableListOf<UploadTask>()
    files.forEach {
        if (it.matches(Regex("$CONTENT_SCHEME.*"))) {
            val id = UUID.randomUUID().toString()
            val filesStoragePath = "${collection}${id}"
            val filesStorage = FirebaseStorage.getInstance().reference.child(filesStoragePath)
            tasks.add(filesStorage.putFile(Uri.parse(it)))
        } else
            filesRes.add(it)
    }

    Tasks.whenAllComplete(tasks).await()
    tasks.forEach { task ->
        filesRes.add(task.await().storage.downloadUrl.await().toString())
    }

    return filesRes
}

suspend fun deleteFilesFromFirebaseStorage(
    files: List<Uri>
 ): String {
    val tasks = mutableListOf<Task<Void>>()
    files.forEach {
        val filesStorage = FirebaseStorage.getInstance().reference.storage
        tasks.add(filesStorage.getReferenceFromUrl(it.toString()).delete())
    }

    var res = ""
    Tasks.whenAllComplete(tasks).await()
    tasks.forEach { task ->
        if (!task.isSuccessful)
            res += "," + task.exception
    }

    return res
}

fun getHouses(
    coroutineScope: CoroutineScope
) {
    emitHouseMessageLoading(coroutineScope)

    val collectionRef = Firebase.firestore.collection(HOUSES_COLLECTION)

    collectionRef
        .get()
        .addOnSuccessListener { result ->
            val resultObjects = result.toObjects(House::class.java)
            emitHouseMessageSuccess(coroutineScope, resultObjects)
        }.addOnFailureListener {
            emitHouseMessageError(coroutineScope, it)
        }
}

fun addHouse(
    coroutineScope: CoroutineScope,
    house: House
) {
    val currUser = userGetCurrent() ?: return

    emitHouseMessageLoading(coroutineScope)

    val collectionRef = Firebase.firestore.collection(HOUSES_COLLECTION)

    coroutineScope.launch {
        async {
            house.images = uploadFilesToFirebaseStorage(house.images, "$HOUSES_COLLECTION/$IMAGES_PATH/")
        }.await()

        collectionRef
            .whereEqualTo(HOUSE_ID_FIELD, house.id)
            .get()
            .addOnSuccessListener { results ->
                when (results.size()) {
                    0 -> collectionRef
                        .add(house)
                        .addOnSuccessListener {
                            emitHouseMessageSuccess(coroutineScope)
                        }.addOnFailureListener {
                            emitHouseMessageError(coroutineScope, it)
                        }

                    1 -> results.forEach { result ->
                        collectionRef.document(result.id)
                            .set(house)
                            .addOnSuccessListener {
                                emitHouseMessageSuccess(coroutineScope)
                            }
                            .addOnFailureListener {
                                emitHouseMessageError(coroutineScope, it)
                            }
                    }

                    else -> emitHouseMessageError(coroutineScope, Exception(results.toString()))
                }
            }
            .addOnFailureListener {
                emitHouseMessageError(coroutineScope, it)
            }
    }
}

fun deleteHouse(coroutineScope: CoroutineScope, house: House) {
    emitHouseMessageLoading(coroutineScope)

    Firebase.firestore.collection(HOUSES_COLLECTION)
        .whereEqualTo(HOUSE_ID_FIELD, house.id)
        .get()
        .addOnSuccessListener { results ->
            results.forEach { result ->
                result.reference.delete()
                    .addOnSuccessListener {
                        coroutineScope.launch {
                            var res = ""
                            async {
                                res = deleteFilesFromFirebaseStorage(house.images.map { Uri.parse(it) })
                            }.await()
                            if (res == "")
                                emitHouseMessageSuccess(coroutineScope)
                            else
                                emitHouseMessageError(coroutineScope, Exception(res))
                        }
                    }
                    .addOnFailureListener {
                        emitHouseMessageError(coroutineScope, it)
                    }
            }
        }
        .addOnFailureListener {
            emitHouseMessageError(coroutineScope, it)
        }
}

fun <FirebaseDataType> emitMessage (
    coroutineScope: CoroutineScope,
    flow: MutableSharedFlow<FlowMessage<FirebaseDataType>>,
    messageType: MESSAGE_TYPE,
    message: String = "",
    data: FirebaseDataType? = null
){
    coroutineScope.launch {
        val flowMessage = if (messageType == FIREBASE_ERROR) firebaseErrorMessage(message) else message
        val flowData = FlowMessage(data, messageType, flowMessage)
        flow.emit(flowData)
    }
}

fun emitUserMessage (
    coroutineScope: CoroutineScope,
    messageType: MESSAGE_TYPE,
    message: String = "",
    data: FirebaseUser? = null
){
    emitMessage(coroutineScope, _firebaseFlowUser, messageType, message, data)
}

fun emitUserMessageLoading (
    coroutineScope: CoroutineScope
){
    emitUserMessage(coroutineScope, FIREBASE_LOADING)
}

fun emitUserMessageSuccess (
    coroutineScope: CoroutineScope,
    data: FirebaseUser? = null,
    message: String = ""
){
    emitUserMessage(coroutineScope, FIREBASE_SUCCESS, message, data)
}

fun emitUserMessageError (
    coroutineScope: CoroutineScope,
    exception: Exception
){
    emitUserMessage(coroutineScope, FIREBASE_ERROR, firebaseErrorMessage(exception.message.toString()))
}

fun emitHouseMessage (
    coroutineScope: CoroutineScope,
    messageType: MESSAGE_TYPE,
    message: String = "",
    data: List<House>? = null
){
    emitMessage(coroutineScope, _firebaseFlowHouses, messageType, message, data)
}

fun emitHouseMessageLoading (
    coroutineScope: CoroutineScope
){
    emitHouseMessage(coroutineScope, FIREBASE_LOADING)
}

fun emitHouseMessageSuccess (
    coroutineScope: CoroutineScope,
    data: List<House>? = null,
    message: String = ""
){
    emitHouseMessage(coroutineScope, FIREBASE_SUCCESS, message, data)
}

fun emitHouseMessageError (
    coroutineScope: CoroutineScope,
    exception: Exception
){
    emitHouseMessage(coroutineScope, FIREBASE_ERROR, firebaseErrorMessage(exception.message.toString()))
}

fun firebaseErrorMessage(message: String) : String{
    val strErrorFirebase = NekretnineFeritApp.context!!.getString(R.string.error_firebase)
    return "$strErrorFirebase $message"
}
