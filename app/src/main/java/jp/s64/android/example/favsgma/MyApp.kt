package jp.s64.android.example.favsgma

import android.app.Application
import local.jp.s64.android.example.favsgma.core.MyCoreWrapper

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        MyCoreWrapper.init(this)
    }

}
