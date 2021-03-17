package id.phephen.roomcrud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.phephen.roomcrud.databinding.ItemUserBinding
import id.phephen.roomcrud.model.User

/**
 * Created by phephen on 07,February,2021
 * https://github.com/fendysaputro
 */
class UserAdapter (var users: ArrayList<User>, var listener: OnAdapterListener) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(users[position], listener)
    }

    override fun getItemCount() = users.size

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User, listener: OnAdapterListener) {
            binding.tvName.text = item.name
            binding.tvEmail.text = item.email
            binding.tvPhone.text = item.phone

            itemView.setOnClickListener {
                listener.onClick(item)
            }

            binding.iconDelete.setOnClickListener {
                listener.onDelete(item)
            }
        }
    }

    fun setData(newList: List<User>) {
        users.clear()
        users.addAll(newList)
    }

    interface OnAdapterListener {
        fun onClick(user: User)
        fun onDelete(user: User)
    }
}