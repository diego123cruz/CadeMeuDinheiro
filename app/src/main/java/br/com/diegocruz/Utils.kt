package br.com.diegocruz

import android.content.Context
import android.content.res.Resources

fun getIcon(categoryId: Int) : Int {
    when (categoryId) {
        0 -> return R.drawable.ic_outros
        1 -> return R.drawable.ic_alimentacao
        2 -> return R.drawable.ic_casa
        3 -> return R.drawable.ic_educacao
        4 -> return R.drawable.ic_saude
        5 -> return R.drawable.ic_transport
        6 -> return R.drawable.ic_diversao
        7 -> return R.drawable.ic_cuidado_pessoal
    }
    return R.drawable.ic_outros
}

fun getCategory(cxt: Context?, categoryId: Int) : String {

    var category : String = "Sem categoria"
    if (cxt != null) {
        val res: Resources = cxt!!.resources
        val categoryList = res.getStringArray(R.array.categorias)
        try {
            category = categoryList[categoryId].toString()
        }
        catch (e : Exception){
            category = "Erro categoria"
        }
    }

    return category
}