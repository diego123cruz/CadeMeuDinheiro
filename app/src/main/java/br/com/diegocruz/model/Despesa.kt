package br.com.diegocruz.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.*

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