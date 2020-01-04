package com.example.tabapplication.ui.main.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.fragment.NumberFragment
import com.thesimplycoder.imagegallery.adapter.Image
import kotlinx.android.synthetic.main.item_phonenumber.view.*



class NumberAdapter(val users: List<NumberFragment.Contact>,
                    val onClick: (NumberFragment.Contact) -> Unit) : RecyclerView.Adapter<NumberAdapter.UserViewHolder>() {

    override fun getItemCount(): Int {return users.size}
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.userNameText.text =
            users[position].name
        holder.userPhoneText.text =
            users[position].phoneNumber
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val holder = UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_phonenumber,
                parent,
                false
            )
        )
        holder.root.setOnClickListener {
            onClick(users[holder.adapterPosition])
        }
        return holder
    }

    class UserViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val userNameText = root.user_name
        val userPhoneText = root.user_phone
    }
}

