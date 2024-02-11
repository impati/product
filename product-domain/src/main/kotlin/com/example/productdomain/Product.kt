package com.example.productdomain

class Product(
    var name: String
) {

    fun changeName(newName: String) {
        this.name = newName;
    }
}
