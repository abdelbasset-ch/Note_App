package com.example.noteapp

class Note {
    var id:Int?=null
    var title:String?=null
    var description:String?=null
    constructor(id:Int,title:String, description:String){
        this.title=title
        this.description=description
        this.id=id
    }
}