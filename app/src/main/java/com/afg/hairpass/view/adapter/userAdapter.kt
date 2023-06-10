package com.afg.hairpass.view.admin

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afg.hairpass.model.UserModel
import kotlinx.android.synthetic.main.list_user.view.*

class userAdapter (private val users:List<UserModel>): RecyclerView.Adapter<usersHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): usersHolder {
        return usersHolder(
            View.inflate(parent.context, com.afg.hairpass.R.layout.list_user, null)
        )
    }

    override fun onBindViewHolder(holder: usersHolder, position: Int) {
       holder.bindUser(users[position])
    }

    override fun getItemCount(): Int = users.size


}

class usersHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val email = view.tvEmail
private val phoneNumber = view.tvPhoneNumber
    private val nama = view.nama

    fun bindUser(user:UserModel){
        email.text = user.email
        phoneNumber.text = user.PhoneNumber
        nama.text = user.nama


    }
}