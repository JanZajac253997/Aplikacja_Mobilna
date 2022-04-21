package com.example.wersja_1_roslinki

class Notki {
    var ID:Int?=null
    var NoteName:String?=null
    var NoteTemp_low:Int?=null
    var NoteTemp_high:Int?=null
    var NoteHum_low:Int?=null
    var NoteHum_high:Int?=null
    var NoteHum_gleby_low:Int?=null
    var NoteHum_gleby_high:Int?=null

    constructor(ID:Int,NoteName:String,NoteTemp_low:Int,NoteTemp_high:Int,NoteHum_low:Int,NoteHum_high:Int,NoteHum_gleby_low:Int,NoteHum_gleby_high:Int){
        this.ID=ID
        this.NoteName=NoteName
        this.NoteTemp_low=NoteTemp_low
        this.NoteTemp_high=NoteTemp_high
        this.NoteHum_low=NoteHum_low
        this.NoteHum_high=NoteHum_high
        this.NoteHum_gleby_low=NoteHum_gleby_low
        this.NoteHum_gleby_high=NoteHum_gleby_high
    }

}