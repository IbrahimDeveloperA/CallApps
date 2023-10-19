package com.on.callapps.ui.live

import android.os.Bundle
import android.app.Activity
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.on.callapps.R
import com.on.callapps.databinding.FragmentLiveBinding
import com.on.callapps.ui.live.adapter.LiveAdapter

class LiveFragment : Fragment() {

    private lateinit var bindgin: FragmentLiveBinding
    private val adapter by lazy { LiveAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindgin = FragmentLiveBinding.inflate(inflater, container, false)
        return bindgin.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindgin.recyclerView.adapter = adapter
        adapter.addData()
    }

}
