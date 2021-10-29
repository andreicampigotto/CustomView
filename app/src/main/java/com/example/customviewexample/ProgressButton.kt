package com.example.customviewexample

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.customviewexample.databinding.ProgressButtonBinding

//jvmoverloads, gera automaticamente os contrutores em java
class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0

) : ConstraintLayout(
    context, attrs, defStyleAttr
) {
    private var tittle: String? = null
    private var loadingTittle: String? = null

    private val binding = ProgressButtonBinding.inflate(LayoutInflater.from(context), this, true)


    private var state: ProgressButtonState = ProgressButtonState.Normal
        set(value) {
            field = value
            refreshState()
        }

    //inicializacao
    init {
        setLayout(attrs)
        refreshState()
    }

    //funcao para setar o layout
    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(
                it,
                R.styleable.ProgressButton
            )

            setBackgroundResource(R.drawable.progress_button_background)

            val tittleResId =
                attributes.getResourceId(
                    R.styleable.ProgressButton_progress_button_tittle,
                    0
                )
            if (tittleResId != 0)
                tittle = context.getString(tittleResId)

            val loadingTittleResId =
                attributes.getResourceId(
                    R.styleable.ProgressButton_progress_button_loading_tittle,
                    0
                )
            if (loadingTittleResId != 0)
                tittle = context.getString(loadingTittleResId)

            attributes.recycle()

        }
    }

    //funcao para atualizar o estado
    private fun refreshState() {
        isEnabled = state.isEnabled
        isClickable = state.isEnabled
        refreshDrawableState()

        binding.buttonTittle.run {
            isEnabled = state.isEnabled
            isClickable = state.isEnabled
        }

        binding.progressButton.visibility = state.progressVisibility

        when (state) {
            ProgressButtonState.Normal -> binding.buttonTittle.text = tittle
            ProgressButtonState.Loading -> binding.buttonTittle.text = loadingTittle
        }
    }


    //
    sealed class ProgressButtonState(val isEnabled: Boolean, val progressVisibility: Int) {
        object Normal : ProgressButtonState(true, View.GONE)
        object Loading : ProgressButtonState(false, View.VISIBLE)
    }
}