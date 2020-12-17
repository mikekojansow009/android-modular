package com.food.feature.question

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.question.*

/**
 * Created by mikekojansow on 08/10/20.
 * Senior Android Developer
 */
class DialogQuestion(
    private val title: String,
    private val message: String,
    private val btnCancelText: String? = null,
    private val btnOkText: String? = null,
    private val onButtonCancelClicked: () -> Unit = {},
    private val onButtonOkClicked: () -> Unit = {}
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        isCancelable = false

        return inflater.inflate(R.layout.question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSubviews()
    }

    private fun setSubviews() {
        tv_title.text = title
        tv_message.text = message

        btnCancelText?.let {
            btn_cancel.setTextButton(it)
        }

        btnOkText?.let {
            btn_ok.setTextButton(it)
        }

        btn_ok.setClickListener {
            onButtonOkClicked()
            dismissAllowingStateLoss()
        }

        btn_cancel.setClickListener {
            onButtonCancelClicked()
            dismissAllowingStateLoss()
        }
    }


}