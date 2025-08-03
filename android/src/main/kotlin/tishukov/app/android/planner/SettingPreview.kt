package tishukov.app.android.planner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.tishukov.planner.settings.compose.SettingsContent
import com.tishukov.planner.settings.SettingsContract

@Composable
@Preview
private fun SettingPreview() {
    val uiState = remember { mutableStateOf(SettingsContract.State.getEmpty()) }

    SettingsContent(
        uiState = uiState,
    ) { }
}
