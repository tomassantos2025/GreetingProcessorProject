package com.example.app

import annotations.Greeting

open class MyClass {

    @Greeting("Hello from MyClass!")
    open fun sayHello() {
        println("Executing sayHello method")
    }

    @Greeting("Welcome to compute!")
    open fun compute() {
        println("Computing something...")
    }
}
