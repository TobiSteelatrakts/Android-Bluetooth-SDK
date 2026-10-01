package com.pos.bluetoothsdkdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pos.bluetooth_sdk.BluetoothCallback
import com.pos.bluetooth_sdk.BluetoothManager
import com.pos.bluetoothsdkdemo.ui.theme.BluetoothSDKDemoTheme
import com.pos.bluetooth_sdk.BluetoothSDK
class MainActivity : ComponentActivity() {

//    private lateinit var bluetoothManager: BluetoothManager
//    private  var bluetoothManager: BluetoothSDK
private var bluetoothEnabled by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        bluetoothManager = BluetoothManager(this);
        BluetoothSDK.initialize(this)

//        val enabled = bluetoothManager.isBluetoothEnabled()
//
//        println("Bluetooth enabled: $enabled")

        val enabled = BluetoothSDK.isBluetoothEnabled()

        bluetoothEnabled = enabled;
        println("Bluetooth enabled: $enabled")

        var bluetoothCallback = object : BluetoothCallback {

            override fun onBluetoothStateChanged(
                enabled: Boolean
            ) {
                bluetoothEnabled = enabled;
                println("SDK says the Bluetooth: $enabled")
            }
        }
//        bluetoothManager.startMonitoring(
//            bluetoothCallback   
//        )
        BluetoothSDK.startMonitoring(
            bluetoothCallback
        )
        enableEdgeToEdge()
        setContent {

            BluetoothSDKDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                    Column {
                        Button(
                            onClick = {
                                BluetoothSDK.stopMonitoring()
                            }
                        ) {
                            Text("Stop Monitoring")
                        }
                        Spacer(Modifier.padding(20.dp))
                        Button(
                            onClick = {
                                BluetoothSDK.startMonitoring(bluetoothCallback)
                            }
                        ) {
                            Text("Start Monitoring")
                        }
                        Spacer(Modifier.padding(20.dp))
                        Button(
                            onClick = {
                                BluetoothSDK.release()
                            }
                        ) {
                            Text("Release / stop Monitoring")

                        }

                        Spacer(Modifier.padding(20.dp))
                        Text("The Bluetooth is currently:${ if(bluetoothEnabled) "Enabled" else "Disabled" }")

                    }

                }
            }
        }
    }


    override fun onDestroy() {
        BluetoothSDK.release()
        super.onDestroy()
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BluetoothSDKDemoTheme {
        Greeting("Android")
    }
}