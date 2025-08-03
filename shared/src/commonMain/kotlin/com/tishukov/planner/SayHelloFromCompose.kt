package com.tishukov.planner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SayHelloFromCompose(
    viewModel: AppViewModel = viewModel { AppViewModel() }
) {

    val state = viewModel.uiState.collectAsState()

    Column(modifier = Modifier.size(200.dp)) {
        TextWithState(stateText = state)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.onClick() },
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        ) {
            Text("Click me")
        }
    }
}

@Composable
private fun TextWithState(stateText: State<String>) {
    val text = stateText.value

    Text(text = text)
}

