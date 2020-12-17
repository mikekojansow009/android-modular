package com.food.dialog.error

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.error.*

/**
 * Created by mikekojansow on 04/08/20.
 * Senior Android Developer
 */
class DialogError(
    private val title: String,
    private val message: String,
    private val btnText: String,
    private val onButtonClick: () -> Unit = {}
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        isCancelable = false

        return inflater.inflate(R.layout.error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSubviews()
    }

    private fun setSubviews() {
        tv_title.text = title
        tv_message.text = message

        btn_ok.setTextButton(btnText)
        btn_ok.setClickListener {
            onButtonClick
            dismissAllowingStateLoss()
        }
    }
}