package com.food.core.base.fragment

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.food.core.base.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by mikekojansow on 10/09/20.
 * Senior Android Developer
 */
open class BaseBottomSheetDialog(
    private val isNeedFullLayout: Boolean = true,
    private val canHide: Boolean = true
) : BottomSheetDialogFragment() {


    var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = canHide

        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog

            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout

            if(isNeedFullLayout) {
                val constraintLayout = bottomSheet.parent as CoordinatorLayout
                constraintLayout.parent.requestLayout()

                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
                bottomSheetBehavior?.isFitToContents = true
            } else {
                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            }

            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior?.isHideable = canHide
        }

        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimationDownUp

        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}