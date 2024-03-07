package com.example.driver.presentation.main.add_edit.components

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.driver.R
import com.example.driver.presentation.util.ChooserIntentProvider
import com.example.driver.presentation.util.ComposeFileProvider

@Composable
fun AttachmentsField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    attachmentsList: List<String>,
    onAttachmentsListChanged: (List<String>) -> Unit
) {
    val context = LocalContext.current

    var defaultUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val chooser = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            if (it.resultCode != Activity.RESULT_OK) {
                return@rememberLauncherForActivityResult
            }

            it.data?.data?.let { source ->
                    ComposeFileProvider.getPdfFileUriIfRequired(context, source)?.let { newUri ->
                        defaultUri = newUri
                    }
                    defaultUri?.let { dest ->
                        ComposeFileProvider.copyFile(context, source, dest)
                    }
                }

            defaultUri?.let {
                onAttachmentsListChanged(
                    attachmentsList
                        .toMutableList().apply { add(it.lastPathSegment.orEmpty()) }
                        .toList()
                )
            }
        }
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .clickable(
                    enabled = enabled
                ) {
                    val uri = ComposeFileProvider.getFileUri(context)
                    defaultUri = uri

                    chooser.launch(
                        ChooserIntentProvider.createIntent(context, uri)
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "",
                modifier = Modifier.padding(4.dp),
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = stringResource(id = R.string.attachments_label),
                style = MaterialTheme.typography.body1
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .height(90.dp)
        ) {
            items(attachmentsList.size) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .weight(6f)
                            .align(Alignment.CenterVertically),
                        text = attachmentsList[index],
                        style = MaterialTheme.typography.body2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "",
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .clickable {
                            onAttachmentsListChanged(
                                attachmentsList
                                    .toMutableList().apply { removeAt(index) }
                                    .toList()
                            )},
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}
