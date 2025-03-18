package tishukov.app.android.planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tishukov.app.comman_main.planner.SayHelloFromCompose

class RootActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SayHelloFromCompose()
        }
    }
}
