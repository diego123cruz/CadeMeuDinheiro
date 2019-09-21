package br.com.diegocruz.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.*


/*
Despesa

id - Int
id_extenal - Int
created_at - Date
created_by - Date
category - Int
description - String
value - Double
 */

open class Despesa : RealmObject() {
    @PrimaryKey
    var id : Long = 0
    var id_external : Int = 0
    var created_at : Date = Date()
    var created_by : Long = 0
    var category : Int = 0
    var description : String = ""
    var valeu_despesa : Double = 0.0
    @Ignore
    var showDate : Boolean = false // help layout...??
}