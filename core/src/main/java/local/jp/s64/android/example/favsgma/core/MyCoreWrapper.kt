package local.jp.s64.android.example.favsgma.core

import android.app.Application
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

typealias AdUnitId = String

object MyCoreWrapper {

    fun init(app: Application) {
        MobileAds.initialize(app.applicationContext)
    }

    fun newRv(context: Context): MyCoreInstance {
        return MyCoreInstanceInternal(
            rv = MobileAds.getRewardedVideoAdInstance(context)
                .apply {

                }
        )
    }

}

interface MyCoreInstance {

    suspend fun load(unitId: AdUnitId): Boolean
    suspend fun show(): Boolean

}

private data class MyCoreInstanceInternal(
    private val rv: RewardedVideoAd
) : MyCoreInstance {

    private val listeners = mutableSetOf<Listener>()

    private interface Listener {

        fun onClosed() {
            // default no-op
        }

        fun onLeftApp() {
            // default no-op
        }

        fun onLoaded() {
            // default no-op
        }

        fun onOpened() {
            // default no-op
        }

        fun onCompleted() {
            // default no-op
        }

        fun onRewarded() {
            // default no-op
        }

        fun onStarted() {
            // default no-op
        }

        fun onFailedToLoad() {
            // default no-op
        }

    }

    init {
        rv.rewardedVideoAdListener = object : RewardedVideoAdListener {

            override fun onRewardedVideoAdClosed() {
                listeners.forEach { x ->
                    x.onClosed()
                }
            }

            override fun onRewardedVideoAdLeftApplication() {
                listeners.forEach { x ->
                    x.onLeftApp()
                }
            }

            override fun onRewardedVideoAdLoaded() {
                listeners.forEach { x ->
                    x.onLoaded()
                }
            }

            override fun onRewardedVideoAdOpened() {
                listeners.forEach { x ->
                    x.onOpened()
                }
            }

            override fun onRewardedVideoCompleted() {
                listeners.forEach { x ->
                    x.onCompleted()
                }
            }

            override fun onRewarded(p0: RewardItem?) {
                listeners.forEach { x ->
                    x.onRewarded()
                }
            }

            override fun onRewardedVideoStarted() {
                listeners.forEach { x ->
                    x.onStarted()
                }
            }

            override fun onRewardedVideoAdFailedToLoad(p0: Int) {
                listeners.forEach { x ->
                    x.onFailedToLoad()
                }
            }
        }
    }

    override suspend fun load(unitId: AdUnitId) = suspendCoroutine<Boolean> { cont ->
        listeners.add(
            object : Listener {

                override fun onLoaded() {
                    resumeResult()
                }

                override fun onFailedToLoad() {
                    resumeResult()
                }

                private fun resumeResult() {
                    try {
                        cont.resume(
                            rv.isLoaded
                        )
                    } finally {
                        listeners.remove(this)
                    }
                }

            }
        )
        rv.loadAd(
            unitId,
            AdRequest.Builder()
                .build()
        )
    }

    override suspend fun show() = suspendCoroutine<Boolean> { cont ->
        listeners.add(
            object : Listener {

                override fun onClosed() {
                    resumeResult(true)
                }

                override fun onFailedToLoad() {
                    resumeResult(false)
                }

                private fun resumeResult(result: Boolean) {
                    try {
                        cont.resume(result)
                    } finally {
                        listeners.remove(this)
                    }
                }

            }
        )
        rv.show()
    }

}
