package com.pos.bluetooth_sdk

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class BluetoothManager(
    private val context: Context
) {

    private var isMonitoring = false
    private var callback: BluetoothCallback? = null
    private val bluetoothAdapter =
        BluetoothAdapter.getDefaultAdapter()
    private val bluetoothFilter =
        IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)

    private val bluetoothReceiver = object : BroadcastReceiver() {

        override fun onReceive(
            context: Context?,
            intent: Intent?
        ) {

            val state = intent?.getIntExtra(
                BluetoothAdapter.EXTRA_STATE,
                BluetoothAdapter.ERROR
            )

            when (state) {

                BluetoothAdapter.STATE_ON -> {
                    callback?.onBluetoothStateChanged(true)
                    println("Bluetooth turned ON")
                }

                BluetoothAdapter.STATE_OFF -> {
                    callback?.onBluetoothStateChanged(false)
                    println("Bluetooth turned OFF")
                }
            }
        }
    }

    fun startMonitoring(callback: BluetoothCallback) {

        if (isMonitoring) {
            return
        }

        this.callback = callback;
        // register the receiver
        context.registerReceiver(
            bluetoothReceiver,
            bluetoothFilter
        )


        isMonitoring = true

         // auto notify bluttoth state change
        callback.onBluetoothStateChanged(
            isBluetoothEnabled()
        )
    }

    fun stopMonitoring() {
        if (!isMonitoring) {
            return
        }

        context.unregisterReceiver(bluetoothReceiver)

        println("Stopped Monitoring")
        isMonitoring = false
        callback = null
    }

    fun release() {
        stopMonitoring()
    }
    fun getCurrentState(): Boolean {
        return isBluetoothEnabled()
    }

    fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled == true
    }
}