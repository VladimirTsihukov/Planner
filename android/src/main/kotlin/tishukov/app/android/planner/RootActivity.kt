package tishukov.app.android.planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import com.tishukov.planner.DeviceInfo
import com.tishukov.planner.SayHelloFromCompose

class RootActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val deviceInfo = remember { DeviceInfo() }
            Column {
                Text(text = "Hello from ${deviceInfo.name}!")
                SayHelloFromCompose()
            }
        }
    }
}
