package com.pos.bluetooth_sdk

import android.content.Context

object BluetoothSDK {

    private var initialized = false
    private lateinit var context: Context
    private lateinit var bluetoothManager: BluetoothManager

    fun initialize(context: Context) {
//        this.context = context
//        bluetoothManager = BluetoothManager(context)

        if (initialized) {
            return
        }

        bluetoothManager = BluetoothManager(context)

        initialized = true
        println("Bluetooth SDK initialized")
    }

    fun isBluetoothEnabled(): Boolean {
        return bluetoothManager.isBluetoothEnabled()
    }

    fun startMonitoring(callback: BluetoothCallback) {
        bluetoothManager.startMonitoring(callback)
    }

    fun stopMonitoring() {
        bluetoothManager.stopMonitoring()
    }
    fun release() {
        bluetoothManager.release()
        initialized = false
    }
}