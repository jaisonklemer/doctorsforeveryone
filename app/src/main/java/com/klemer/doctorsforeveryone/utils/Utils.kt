package com.klemer.doctorsforeveryone.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import com.klemer.doctorsforeveryone.R
import ir.drax.netwatch.NetWatch
import ir.drax.netwatch.cb.NetworkChangeReceiver_navigator

fun changeBehaviorForConnection(
    parent: ViewGroup,
    activity: FragmentActivity,
    @IdRes containerId: Int
) {
    NetWatch.builder(parent.context)
        .setCallBack(object : NetworkChangeReceiver_navigator {
            override fun onConnected(source: Int) {
                setViewEnable(activity, containerId, true)
            }

            override fun onDisconnected(): View? {
                setViewEnable(activity, containerId, false)

                return LayoutInflater.from(parent.context)
                    .inflate(R.layout.no_connection, parent, false)
            }
        })
        .setNotificationEnabled(false)
        .build()
}

private fun setViewEnable(activity: FragmentActivity, @IdRes container: Int, enabled: Boolean) {

    activity.findViewById<FrameLayout>(container).visibility =
        if (enabled) View.VISIBLE else View.GONE
}