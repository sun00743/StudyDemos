package com.hd123.kds.model

class User(val userName: String,
           val pwd: String,
           val dToken: String,
           val storeNoList: List<String> = arrayListOf()) {
}