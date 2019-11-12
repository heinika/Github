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
class RxTest7 {

    @Test
    fun baseFlowable() {
        val flowable = Flowable.create(FlowableOnSubscribe<Int> {
            //BackpressureStrategy.ERROR 默认缓存 128
            for (i in 0 .. 129){
                println("emitter $i")
                it.onNext(i)
            }
            it.onComplete()
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
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