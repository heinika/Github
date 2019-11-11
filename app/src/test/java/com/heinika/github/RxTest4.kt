package com.heinika.github

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.junit.Test


class RxTest4 {

    /**
     * 因为在同一个线程里，所以 1 都执行完了，2 才开始执行
     */
    @Test
    fun testZip() {
        val observable1 = Observable.create<Int> { emitter ->
            println("Emitter 1")
            emitter.onNext(1)
            println("Emitter 2")
            emitter.onNext(2)
            println("Emitter 3")
            emitter.onNext(3)
        }

        val observable2 = Observable.create<String> { emitter ->
            println("Emitter A")
            emitter.onNext("A")
            println("Emitter B")
            emitter.onNext("B")
            println("Emitter C")
            emitter.onNext("c")
        }

        Observable.zip(observable1, observable2, BiFunction<Int, String, String> { it1, it2 ->
            return@BiFunction "$it1 $it2"
        }).subscribe {
            println(it)
        }
    }

    @Test
    fun testZipOnIOThread() {
        val observable1 = Observable.create<Int> { emitter ->
            println("Emitter 1")
            emitter.onNext(1)
            Thread.sleep(100)
            println("Emitter 2")
            emitter.onNext(2)
            Thread.sleep(100)
            println("Emitter 3")
            emitter.onNext(3)
            Thread.sleep(100)
            println("Emitter 4")
            emitter.onNext(4)
            Thread.sleep(100)
            println("Emitter onComplete")
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())

        val observable2 = Observable.create<String> { emitter ->
            println("Emitter A")
            emitter.onNext("A")
            Thread.sleep(100)
            println("Emitter B")
            emitter.onNext("B")
            Thread.sleep(100)
            println("Emitter C")
            emitter.onNext("c")
            Thread.sleep(100)
        }.subscribeOn(Schedulers.io())

        Observable.zip(observable1, observable2, BiFunction<Int, String, String> { it1, it2 ->
            return@BiFunction "$it1 $it2"
        }).subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
                print("onComplete")
            }

            override fun onNext(t: String) {
                println(t)
            }

        })

        Thread.sleep(1000)
    }
}