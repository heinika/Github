package com.heinika.github

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RxTest5 {

    @Test
    fun testZipProblem() {
        val observable1 = Observable.create<Int> {
            for (i in 1..Int.MAX_VALUE) {
                println("emitter $i")
                it.onNext(i)
            }
        }.subscribeOn(Schedulers.io())

        val observable2 = Observable.create<String> {
            println("emitter A")
            it.onNext("A")
        }.subscribeOn(Schedulers.io())

        Observable.zip(observable1, observable2, BiFunction<Int, String, String> { it1, it2 ->
            return@BiFunction "$it1 $it2"
        }).observeOn(AndroidSchedulers.mainThread()).subscribe({
            println(it)
        }, {
            println(it.message)
        })
    }
}