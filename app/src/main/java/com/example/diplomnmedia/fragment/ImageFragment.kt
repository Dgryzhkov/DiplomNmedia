package com.example.diplomnmedia.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.diplomnmedia.R
import com.example.diplomnmedia.databinding.FragmentDisplayingImagesBinding
import com.example.diplomnmedia.util.StringArg

class ImageFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDisplayingImagesBinding.inflate(
            inflater,
            container,
            false
        )

        val url = arguments?.textArg
        Glide.with(this)
            .load(url)
            .error(R.drawable.ic_no_image_24)
            .timeout(10_000)
            .into(binding.imageView)

        return binding.root
    }

    companion object {
        var Bundle.textArg: String? by StringArg
    }
}