package com.example.bismillah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList: ArrayList<User>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent, false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

        val user : User = userList[position]
        holder.nameView.text = user.name
        holder.imageView.setImageResource(user.image)
        holder.captionView.text = user.caption

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(user)
        }

    }

    override fun getItemCount(): Int {

        return userList.size

    }
    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val nameView : TextView = itemView.findViewById(R.id.username)
        val imageView : ImageView = itemView.findViewById(R.id.firebaseImage)
        val captionView : TextView = itemView.findViewById(R.id.description)


    }
}