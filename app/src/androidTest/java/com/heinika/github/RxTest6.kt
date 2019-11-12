package com.heinika.github

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.runOnUiThread
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class RxTest6 {

    @Test
    fun fixZipProblemBySample() {
        val observable1 = Observable.create<Int> {
            for (i in 1..Int.MAX_VALUE) {
                it.onNext(i)
            }
        }.subscribeOn(Schedulers.io()).sample(2, TimeUnit.SECONDS)

        val observable2 = Observable.create<String> {
            it.onNext("A")
        }.subscribeOn(Schedulers.io())

        Observable.zip(observable1, observable2, BiFunction<Int, String, String> { it1, it2 ->
            return@BiFunction "Result $it1 $it2"
        }).observeOn(AndroidSchedulers.mainThread()).subscribe({
            println(it)
        }, {
            println(it.message)
        })
        Thread.sleep(6000)
    }

    @Test
    fun fixZipProblemBySlow() {
        val observable1 = Observable.create<Int> {
            for (i in 1..Int.MAX_VALUE) {
                it.onNext(i)
                Thread.sleep(100)
            }
        }.subscribeOn(Schedulers.io())

        val observable2 = Observable.create<String> {
            it.onNext("A")
        }.subscribeOn(Schedulers.io())

        Observable.zip(observable1, observable2, BiFunction<Int, String, String> { it1, it2 ->
            return@BiFunction "Result $it1 $it2"
        }).observeOn(AndroidSchedulers.mainThread()).subscribe({
            println(it)
        }, {
            println(it.message)
        })

        Thread.sleep(6000)
    }
}