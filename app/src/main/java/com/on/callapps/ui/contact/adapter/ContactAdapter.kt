package com.on.callapps.ui.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.on.callapps.R
import com.on.callapps.data.Contact
import com.on.callapps.databinding.ItemContactBinding

class ContactAdapter : Adapter<ContactAdapter.ContactViewHolder>() {

    private var list = mutableListOf<Contact>(
        Contact(
            R.drawable.img,
            "Character 1",
            "+8404581223",
            R.drawable.vector
        ),
        Contact(
            R.drawable.img,
            "Character 1",
            "+8404581223",
            R.drawable.vector
        ),
        Contact(
            R.drawable.img,
            "Character 1",
            "+8404581223",
            R.drawable.vector,"0/2"
        ),
        Contact(
            R.drawable.img,
            "Character 1",
            "+8404581223",
            R.drawable.vector,"1/4"
        ),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class ContactViewHolder(private var binding: ItemContactBinding) :
        ViewHolder(binding.root) {
        fun onBind(contact: Contact) {
            binding.tvCharacter.text = contact.name
            binding.tvNumber.text = contact.number
            binding.imgOk.setImageResource(contact.imgOk)
            binding.imgView.setImageResource(contact.imgProfile)
        }

    }
}

