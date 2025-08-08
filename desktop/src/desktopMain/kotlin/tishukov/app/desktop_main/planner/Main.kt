package tishukov.app.desktop_main.planner

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.tishukov.planner.root.compose.RootScreen

fun main() {
    application {
        val state = rememberWindowState().apply { size = DpSize(600.dp, 400.dp) }
        Window(
            onCloseRequest = { exitApplication() },
            state = state,
            title = "Planner",
        ) {
            RootScreen()
        }
    }
}
