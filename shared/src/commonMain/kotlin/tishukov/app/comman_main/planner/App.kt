package tishukov.app.comman_main.planner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun seyHello() {
    println("Hello from common code 😈")
}

@Composable
fun SayHelloFromCompose() {
    Box(modifier = Modifier.size(200.dp)) {
        Text(
            text = "Hello from Compose common",
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
