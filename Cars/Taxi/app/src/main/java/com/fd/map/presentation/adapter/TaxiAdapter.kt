package com.fd.map.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fd.map.R
import com.fd.map.databinding.ItemTaxiBinding
import com.fd.map.domain.models.Poi


class TaxiAdapter : RecyclerView.Adapter<TaxiAdapter.TaxiViewHolder>() {


    inner class TaxiViewHolder(val binding: ItemTaxiBinding) : RecyclerView.ViewHolder(binding.root)


    private val differCallback = object : DiffUtil.ItemCallback<Poi>() {
        override fun areItemsTheSame(oldItem: Poi, newItem: Poi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Poi, newItem: Poi): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaxiViewHolder {
        return TaxiViewHolder(
            ItemTaxiBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TaxiViewHolder, position: Int) {

        val taxi = differ.currentList[position]

        holder.binding.apply {
            if (taxi.fleetType == "TAXI") {
                ivFleetType.scaleX = 1.0F
                ivFleetType.scaleY = 1.0F
                ivFleetType.setImageResource(R.drawable.taxi)
                tvFleetType.text = root.context.resources.getString(R.string.Taxi)
            } else {
                ivFleetType.scaleX = 1.9F
                ivFleetType.scaleY = 1.9F
                ivFleetType.setImageResource(R.drawable.pooling)
                tvFleetType.text = root.context.resources.getString(R.string.Pooling)
            }

            btnRequest.setOnClickListener {
                onRequestButtonClickListener?.let {
                    it(taxi)
                }
            }
            holder.itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(taxi)
                }
            }
        }
    }

    //Request Button Click Event
    private var onRequestButtonClickListener: ((Poi) -> Unit)? = null
    fun setOnRequestButtonClickListener(listener: (Poi) -> Unit) {
        onRequestButtonClickListener = listener
    }

    //Item Click Event
    private var onItemClickListener: ((Poi) -> Unit)? = null
    fun setonItemClickListener(listener: (Poi) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}