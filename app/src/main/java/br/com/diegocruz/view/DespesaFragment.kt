package br.com.diegocruz.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.diegocruz.R
import br.com.diegocruz.model.Despesa
import br.com.diegocruz.view.ui.despesaslista.DespesasListaFragment
import br.com.diegocruz.viewmodel.DespesaViewModel
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_despesa.*
import kotlinx.android.synthetic.main.item_spinner.*
import java.lang.Exception
import java.util.*

class DespesaFragment : Fragment() {
    private val TAG = "DespesaFragment"

    private var myview : View? = null
    private var despesaViewModel : DespesaViewModel? = null
    private var despesa : Despesa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (despesa == null) {
            despesa = Despesa()
        }

        despesaViewModel = ViewModelProviders.of(this).get(DespesaViewModel::class.java)
        despesaViewModel?.getMsg()?.observe(this, Observer {
            msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        })

    }

    fun setDespesa(despesa: Despesa){
        this.despesa = despesa
    }

    fun setListeners(){
        btn_salvar_despesa.setOnClickListener {
            insertDespesa()
            clearFields()
        }
    }

    fun setSpinner(){

        val spinner: Spinner = spinner_category_despesa
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            context!!,
            R.array.categorias,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter

            spinner.onItemSelectedListener = selectItemCategoria
        }
    }

    val selectItemCategoria = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            if(despesa != null && despesa!!.id == 0L) {
                despesa?.category = pos
            }else{
                val realm = Realm.getDefaultInstance()
                try {
                    realm.beginTransaction()
                    despesa?.category = pos
                    realm.commitTransaction()
                }
                finally {
                    realm.close()
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }
    }

    fun insertDespesa(){
        val descricao = edt_description_despesa.text.toString()
        val value = edt_value_despesa.text.toString().toDouble()

        if(despesa != null && despesa!!.id > 0L) {
            val realm = Realm.getDefaultInstance()
            try {
                realm.beginTransaction()
                despesa?.description = descricao
                despesa?.valeu_despesa = value
                realm.commitTransaction()
            }
            finally {
                realm.close()

                despesa = Despesa()

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, DespesasListaFragment.newInstance())
                    ?.commitNow()

            }
        }else {

            despesa?.description = descricao
            despesa?.valeu_despesa = value

            despesa.let {
                despesaViewModel?.insertDespesa(it!!)
            }
        }
    }

    fun clearFields(){
        try{
            edt_description_despesa.text.clear()
            edt_value_despesa.text.clear()
        }catch (e:Exception){

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_despesa, container, false)
        return myview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        setSpinner()

        if (despesa != null && despesa!!.id != 0L) {
            edt_description_despesa.setText(despesa?.description)
            edt_value_despesa.setText(despesa?.valeu_despesa.toString())
        }
    }

}
