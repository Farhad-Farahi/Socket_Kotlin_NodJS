package com.fd.map.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.fd.map.databinding.BottomsheetFragmentBinding


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = BottomsheetFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNo.setOnClickListener {
            this.dismiss()
        }
        binding.btnYes.setOnClickListener {
            Toast.makeText(context, "Request sent", Toast.LENGTH_SHORT).show()
            this.dismiss()
        }
    }
}