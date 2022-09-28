package com.contactlist.currency.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.contactlist.currency.data.local.entity.ContactEntity
import com.contactlist.currency.databinding.ItemContactBinding

class RvContactListAdapter : RecyclerView.Adapter<RvContactListAdapter.CurrencyViewHolder>(),
    Filterable {

    private var contactList: ArrayList<ContactEntity> = ArrayList()
    var contactListFiltered: ArrayList<ContactEntity> = ArrayList()

    var onContactClicked: ((ContactEntity) -> Unit)? = null
    var onContactDeleteClicked: ((ContactEntity) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val itemBinding =
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(currencyViewHolder: CurrencyViewHolder, position: Int) {
        currencyViewHolder.bind(contactListFiltered[position])
    }

    override fun getItemCount() = contactListFiltered.size

    inner class CurrencyViewHolder(private val itemBinding: ItemContactBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.btnDelete.setOnClickListener {
                onContactDeleteClicked?.invoke(contactListFiltered[adapterPosition])
            }
            itemBinding.root.setOnClickListener {
                onContactClicked?.invoke(contactListFiltered[adapterPosition])
            }
        }

        fun bind(contact: ContactEntity) = with(itemView) {
            itemBinding.tvTitle.text = contact.contactName
            itemBinding.tvAddress.text = contact.address
            itemBinding.phoneNo.text = contact.phone
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refereshList(items: List<ContactEntity>) {
        contactList = items as ArrayList
        contactListFiltered = contactList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                contactListFiltered = if (charString.isEmpty()) contactList else {
                    val filteredList = ArrayList<ContactEntity>()
                    contactList
                        .filter {
                            (it.customerId.contains(constraint!!)) or
                                    (it.contactName.contains(constraint)) or
                                    (it.companyName.contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = contactListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                contactListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<ContactEntity>
                notifyDataSetChanged()
            }
        }

    }
}