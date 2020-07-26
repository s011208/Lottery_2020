package bj4.dev.yhh.lottery.app

import android.app.Application
import bj4.dev.yhh.lottery.BuildConfig
import bj4.dev.yhh.lotteryupdater.WorkerInit
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class LotteryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false);

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            if (FlipperUtils.shouldEnableFlipper(this)) {
                val client = AndroidFlipperClient.getInstance(this)
                client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
                client.addPlugin(DatabasesFlipperPlugin(this))
                client.addPlugin(CrashReporterPlugin.getInstance())
                client.start()
            }
        }

        startKoin {
            androidLogger()
            androidContext(this@LotteryApplication)
            modules(appModule)
        }

        initRxErrorHandler()
        initWorkers()
    }

    private fun initWorkers() {
        WorkerInit(this).start()
    }

    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            Timber.w(it)
            if (it is UndeliverableException) {
                FirebaseCrashlytics.getInstance().recordException(it)
                return@setErrorHandler
            } else {
                throw it
            }
        }
    }
}