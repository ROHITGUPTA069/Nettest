package com.example.ns2

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    private val CHANNEL = "wifi_channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->

            when (call.method) {

                "isWifiConnected" -> {
                    val cm =
                        getSystemService(Context.CONNECTIVITY_SERVICE)
                                as ConnectivityManager
                    val network = cm.activeNetworkInfo
                    result.success(
                        network != null &&
                        network.isConnected &&
                        network.type == ConnectivityManager.TYPE_WIFI
                    )
                }

                "getWifiRssi" -> {
                    val wifiManager =
                        applicationContext.getSystemService(Context.WIFI_SERVICE)
                                as WifiManager
                    result.success(wifiManager.connectionInfo.rssi)
                }

                else -> result.notImplemented()
            }
        }
    }
}
