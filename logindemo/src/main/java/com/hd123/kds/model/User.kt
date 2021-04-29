package com.hd123.kds.model

// TODO: 2021/4/28
class User(val userName: String,
           val pwd: String,
           val dToken: String,
           val storeNoList: List<String> = arrayListOf()) {
}