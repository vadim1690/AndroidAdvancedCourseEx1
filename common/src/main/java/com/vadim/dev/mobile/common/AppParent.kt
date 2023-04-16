package com.vadim.dev.mobile.common

import android.app.Application
import com.vadim.dev.mobile.common.utils.MSP
import com.vadim.dev.mobile.common.utils.MySignalV2

abstract class AppParent : Application() {

    override fun onCreate() {
        super.onCreate()

        MySignalV2.initHelper(this)
        MSP.initHelper(this)
    }
}