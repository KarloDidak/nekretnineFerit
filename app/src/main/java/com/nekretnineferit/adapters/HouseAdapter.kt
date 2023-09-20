package com.nekretnineferit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nekretnineferit.NekretnineFeritApp
import com.nekretnineferit.R
import com.nekretnineferit.data.House
import com.nekretnineferit.databinding.RvItemHouseBinding

class HouseAdapter :
    RecyclerView.Adapter<HouseAdapter.HouseViewHolder>() {

    inner class HouseViewHolder(private val binding: RvItemHouseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(house: House) {
            binding.apply {
                if (house.images.isNotEmpty())
                    Glide.with(itemView).load(house.images[0]).into(ivHouse)

                val appCtx = NekretnineFeritApp.context!!
                tvPrice.text = appCtx.getString(R.string.tv_price_format, house.price.toString())
                tvLocation.text = house.location
                tvArea.text = appCtx.getString(R.string.tv_area_format, house.area.toString())
                tvLocation.text = house.location
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<House>() {
        override fun areItemsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: House, newItem: House): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        return HouseViewHolder(
            RvItemHouseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        val house = differ.currentList[position]
        holder.bind(house)

        holder.itemView.setOnClickListener {
            onClick?.invoke(house)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((House) -> Unit)? = null
}













