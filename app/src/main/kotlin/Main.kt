package com.example.app

fun main() {
    val myClass = MyClass()

    val wrapped = MyClassWrapper(myClass)

    wrapped.sayHello()
    wrapped.compute()
}
