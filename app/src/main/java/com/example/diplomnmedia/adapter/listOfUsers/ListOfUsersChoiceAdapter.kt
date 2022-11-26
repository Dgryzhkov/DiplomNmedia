package com.example.diplomnmedia.adapter.listOfUsers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplomnmedia.R
import com.example.diplomnmedia.databinding.CardMentionsCheckedBinding
import com.example.diplomnmedia.dto.UserRequest

/**
 *@Author Dgryzhkov
 */
interface AdapterCallback {
    fun isChecked(id: Int) {}
    fun unChecked(id: Int) {}
}

class ListOfUsersChoiceAdapter(private val callback: AdapterCallback) :
    ListAdapter<UserRequest, UsersListOfViewHolder>(UsersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListOfViewHolder {
        val binding =
            CardMentionsCheckedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersListOfViewHolder(binding, callback)
    }

    override fun onBindViewHolder(holder: UsersListOfViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class UsersListOfViewHolder
    (
    private val binding: CardMentionsCheckedBinding,
    private val callback: AdapterCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UserRequest) {
        binding.apply {
            Glide.with(binding.root)
                .load(user.avatar)
                .error(R.drawable.ic_no_avatar_48)
                .placeholder(R.drawable.ic_no_user_48)
                .timeout(10_000)
                .circleCrop()
                .into(avatar)

            author.text = user.name

            checkbox.apply {
                isChecked = user.checked
            }

            checkbox.setOnClickListener {
                if (checkbox.isChecked) {
                    callback.isChecked(user.id)
                } else {
                    callback.unChecked(user.id)
                }

            }
        }
    }
}

class UsersDiffCallback : DiffUtil.ItemCallback<UserRequest>() {
    override fun areItemsTheSame(oldItem: UserRequest, newItem: UserRequest): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserRequest, newItem: UserRequest): Boolean {
        return oldItem == newItem
    }
}