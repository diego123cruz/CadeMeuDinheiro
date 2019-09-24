package br.com.diegocruz.view.ui.insertdespesa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.diegocruz.model.Despesa
import io.realm.Realm

class InsertEditDespesaViewModel : ViewModel() {

    private val Sucesso : MutableLiveData<String> = MutableLiveData()
    private val Falha : MutableLiveData<String> = MutableLiveData()

    fun getSucesso() : LiveData<String> {
        return Sucesso
    }

    fun getFalha() : LiveData<String> {
        return Falha
    }

    fun Delete(despesa: Despesa){
        val realm = Realm.getDefaultInstance()
        try{
            realm.beginTransaction()
            val despesaDelete = realm.where(Despesa::class.java).equalTo("id", despesa.id).findFirst()
            despesaDelete?.deleteFromRealm()
            realm.commitTransaction()
        }
        finally {
            realm.close()
            Sucesso.value = "Excluido com sucesso"
        }
    }

    fun InsertEditDespesa(despesa: Despesa){
        val realm = Realm.getDefaultInstance()
        try {
            realm.beginTransaction()
            var nextID : Long? = (realm.where(Despesa::class.java).max("id")) as Long?
            if (nextID == null){
                nextID = 1
            }
            if(despesa.id == 0L) {
                despesa.id = nextID + 1
            }
            //realm.copyToRealm(despesa)
            realm.copyToRealmOrUpdate(despesa)
            realm.commitTransaction()
        }
        finally {
            realm.close()
        }
        Sucesso.value = "Salvo com sucesso"
    }
}
