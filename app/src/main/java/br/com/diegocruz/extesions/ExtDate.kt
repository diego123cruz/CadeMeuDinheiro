package br.com.diegocruz.extesions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formateDDMMAAAA_HHmmString() : String {
    var date = this
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
    return formatter.format(date)
}

fun Date.formateDDMMAA_HHmmString() : String {
    var date = this
    val formatter = SimpleDateFormat("dd/MM/yy HH:mm")
    return formatter.format(date)
}

fun Date.formateHHmmString() : String {
    var date = this
    val formatter = SimpleDateFormat("HH:mm:ss")
    return formatter.format(date)
}

fun Date.formateDDMMAAAAString() : String {
    var date = this
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(date)
}