package com.heinika.github


import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

@RunWith(AndroidJUnit4::class)
class RxTest8 {

    @Test
    fun testFlowableStrategyBuffer() {
        val flowable = Flowable.create(FlowableOnSubscribe<Int> {
            //BackpressureStrategy.BUFFER 默认缓存 无限大 可导致 OOM
            for (i in 0 .. Int.MAX_VALUE){
                println("emitter $i")
                it.onNext(i)
            }
            it.onComplete()
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
        val subscriber = object : Subscriber<Int> {
            override fun onComplete() {
                println("onComplete")
            }

            override fun onSubscribe(s: Subscription?) {
                println("onSubscribe")
                s!!.request(Long.MAX_VALUE)
            }

            override fun onNext(t: Int?) {
                println("Result is $t")
            }

            override fun onError(t: Throwable?) {
                println("onError $t")
            }
        }

        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber)

        Thread.sleep(3000)
    }


    @Test
    fun testFlowableStrategyDrop() {
        val flowable = Flowable.create(FlowableOnSubscribe<Int> {
            //BackpressureStrategy.DROP 默认缓存 128 会丢弃掉事件
            for (i in 0 .. Int.MAX_VALUE){
                println("emitter $i")
                it.onNext(i)
            }
            it.onComplete()
        }, BackpressureStrategy.DROP).subscribeOn(Schedulers.io())
        val subscriber = object : Subscriber<Int> {
            override fun onComplete() {
                println("onComplete")
            }

            override fun onSubscribe(s: Subscription?) {
                println("onSubscribe")
                s!!.request(Long.MAX_VALUE)
            }

            override fun onNext(t: Int?) {
                println("Result is $t")
            }

            override fun onError(t: Throwable?) {
                println("onError $t")
            }
        }

        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber)

        Thread.sleep(3000)
    }


    @Test
    fun testFlowableStrategyLastet() {
        val flowable = Flowable.create(FlowableOnSubscribe<Int> {
            //BackpressureStrategy.DROP 默认缓存 128 会丢弃掉事件 最后一个事件为刚发送的事件
            for (i in 0 .. Int.MAX_VALUE){
                println("emitter $i")
                it.onNext(i)
            }
            it.onComplete()
        }, BackpressureStrategy.LATEST).subscribeOn(Schedulers.io())
        val subscriber = object : Subscriber<Int> {
            override fun onComplete() {
                println("onComplete")
            }

            override fun onSubscribe(s: Subscription?) {
                println("onSubscribe")
                s!!.request(Long.MAX_VALUE)
            }

            override fun onNext(t: Int?) {
                println("Result is $t")
            }

            override fun onError(t: Throwable?) {
                println("onError $t")
            }
        }

        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber)

        Thread.sleep(3000)
    }

}