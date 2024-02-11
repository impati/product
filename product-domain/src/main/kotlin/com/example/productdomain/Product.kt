package com.example.productdomain

class Product(
    var name: String,
    var age: Int
) {

    fun changeName(newName: String) {
        this.name = newName;
    }

    fun changeAge(age: Int) {
        this.age = age;
    }
}
