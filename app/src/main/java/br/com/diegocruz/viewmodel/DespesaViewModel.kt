package br.com.diegocruz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.diegocruz.model.Despesa
import io.realm.Realm

class DespesaViewModel : ViewModel() {

    private val msg : MutableLiveData<String> = MutableLiveData()

    fun getMsg() : LiveData<String> {
        return msg
    }

    fun insertDespesa(despesa: Despesa){
        val realm = Realm.getDefaultInstance()
        try {
            realm.beginTransaction()
            var nextID : Long? = (realm.where(Despesa::class.java).max("id")) as Long?
            if (nextID == null){
                nextID = 1
            }
            if(despesa.id == 0L) {
                despesa.id = nextID as Long + 1
            }
            //realm.copyToRealm(despesa)
            realm.copyToRealmOrUpdate(despesa)
            realm.commitTransaction()
        }
        finally {
            realm.close()
        }
        msg.value = "Salvo com sucesso"
    }
}