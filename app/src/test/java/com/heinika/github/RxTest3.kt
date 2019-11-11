package com.heinika.github

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import org.junit.Test
import java.util.concurrent.TimeUnit


class RxTest3 {

    @Test
    fun testMap() {
        Observable.create<Int> { emitter ->
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onNext(3)
        }.map {
            return@map "The result is $it"
        }.subscribe {
            println(it)
        }
    }

    /**
     * flat 不保证顺序。若需要保证顺序，请使用 concatMap
     */
    @Test
    fun testFlatMap() {
        Observable.create<Int> { emitter ->
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onNext(3)
        }.flatMap(Function<Int, ObservableSource<String>> {
            val list = arrayListOf<String>()
            for (i in 0..2) {
                list.add("I am value $it")
            }
            return@Function Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS)
        }).subscribe {
            println(it)
        }
        Thread.sleep(1000)
    }

    @Test
    fun testConcatMap() {
        Observable.create<Int> { emitter ->
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onNext(3)
        }.concatMap(Function<Int, ObservableSource<String>> {
            val list = arrayListOf<String>()
            for (i in 0..2) {
                list.add("I am value $it")
            }
            return@Function Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS)
        }).subscribe {
            println(it)
        }
        Thread.sleep(1000)
    }
}