package com.on.callapps.ui

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

class LiveFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live, container, false)
    }

}
