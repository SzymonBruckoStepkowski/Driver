package com.example.driver.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import com.example.driver.R

class ChooserIntentProvider {

    companion object {
        private const val ACTION_PICK_TYPE = "image/*"
        private const val GET_CONTENT_INPUT = "application/pdf"

        fun createIntent(
            context: Context,
            uri: Uri,
            intentsArray: Array<Intent> = arrayOf(
                ActivityResultContracts.TakePicture().createIntent(context, uri),
                ActivityResultContracts.GetContent().createIntent(context, GET_CONTENT_INPUT)
            )
        ): Intent {
            return Intent.createChooser(
                Intent(Intent.ACTION_PICK).apply {
                    type = ACTION_PICK_TYPE
                },
                context.getString(R.string.attachment_chooser_text)
            ).apply {
                putExtra(
                    Intent.EXTRA_INITIAL_INTENTS,
                    intentsArray
                )
            }
        }
    }
}