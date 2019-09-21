package br.com.diegocruz.view.ui.despesaslista

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.diegocruz.model.Despesa
import io.realm.Realm

class DespesasListaViewModel : ViewModel() {

    private val despasas : MutableLiveData<List<Despesa>> = MutableLiveData()

    fun getDespesasList() : LiveData<List<Despesa>>{
        return despasas
    }

    fun getDespesas(){
        val realm = Realm.getDefaultInstance()

        val despesasResult = realm.where(Despesa::class.java).findAll()

        despasas.value = despesasResult.toList()
    }
}
