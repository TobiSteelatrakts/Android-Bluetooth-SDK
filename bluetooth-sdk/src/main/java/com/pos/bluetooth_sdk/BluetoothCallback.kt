package com.pos.bluetooth_sdk

interface BluetoothCallback {

    fun onBluetoothStateChanged(enabled: Boolean)
}