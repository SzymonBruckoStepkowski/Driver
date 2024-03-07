package com.example.driver.presentation.main.report_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.driver.R
import com.example.driver.domain.model.Report
import com.example.driver.common.formatToDateTimeString

@Composable
fun ReportListItem(
    modifier: Modifier = Modifier,
    item: Report,
    onItemClick: (Report) -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onItemClick(item) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = modifier
                .weight(1f)
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.createdDate.formatToDateTimeString(),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            modifier = modifier
                .weight(1f)
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (!item.city.isNullOrEmpty()) item.city else stringResource(id = R.string.not_applicable_abbr),
                style = MaterialTheme.typography.body2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            modifier = modifier
                .weight(1f)
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.eventType.label.asString(),
                style = MaterialTheme.typography.body2,
                color = item.eventType.uiColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}