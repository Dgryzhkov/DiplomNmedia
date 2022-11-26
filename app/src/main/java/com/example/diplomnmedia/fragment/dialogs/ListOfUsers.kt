package com.example.diplomnmedia.fragment.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.example.diplomnmedia.R
import com.example.diplomnmedia.adapter.listOfUsers.AdapterCallback
import com.example.diplomnmedia.adapter.listOfUsers.ListOfUsersChoiceAdapter
import com.example.diplomnmedia.databinding.FaragmenListOfUsersBinding
import com.example.diplomnmedia.viewmodel.NewPostViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ListOfUsers: DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FaragmenListOfUsersBinding.inflate(inflater, container, false)

        val newPostViewModel: NewPostViewModel by activityViewModels()

        newPostViewModel.getUsers()

        val adapter = ListOfUsersChoiceAdapter(object : AdapterCallback {
            override fun isChecked(id: Int) {
                newPostViewModel.isChecked(id)
            }
            override fun unChecked(id: Int) {
                newPostViewModel.unChecked(id)
            }
        })
        binding.list.adapter = adapter

        newPostViewModel.dataState.observe(viewLifecycleOwner) { state ->
            if (state.loading){
                Snackbar.make(binding.root, R.string.problem_loading, Snackbar.LENGTH_SHORT).show()
            }
        }

        newPostViewModel.data.observe(viewLifecycleOwner) {
            val newUser = adapter.itemCount < it.size
            adapter.submitList(it) {
                if (newUser) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        binding.enter.setOnClickListener {
            newPostViewModel.addMentionIds()
            findNavController().navigateUp()
        }
        return binding.root
    }


}