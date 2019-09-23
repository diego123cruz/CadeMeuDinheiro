package br.com.diegocruz.view.ui.despesaslista

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.diegocruz.model.Despesa
import io.realm.Realm
import io.realm.Sort

class DespesasListaViewModel : ViewModel() {

    private val despasas : MutableLiveData<List<Despesa>> = MutableLiveData()

    fun getDespesasList() : LiveData<List<Despesa>>{
        return despasas
    }

    fun getDespesas(){
        val realm = Realm.getDefaultInstance()
        try {
            val despesasResultList = realm.where(Despesa::class.java).findAll().sort("created_at", Sort.DESCENDING).toList()

            val copyDespesaList = realm.copyFromRealm(despesasResultList)
            despasas.value = copyDespesaList
        }finally {
            realm.close()
        }
    }
}
