package com.heinika.github

import io.reactivex.Flowable
import org.junit.Test

class RxTest {

    @Test
    fun test(){
        hello("kaka", "heinika")
    }

    fun hello(vararg args: String?) {
        Flowable.fromArray<String>(*args)
            .subscribe { s: String -> println("Hello $s!") }
    }
}


