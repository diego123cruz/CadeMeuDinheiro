package br.com.diegocruz.view.ui.insertdespesa

import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import br.com.diegocruz.R
import br.com.diegocruz.model.Despesa
import br.com.diegocruz.view.adapter.CategorySpinnerAdapter
import kotlinx.android.synthetic.main.insert_despesa_fragment.*

class InsertEditDespesaFragment : Fragment() {

    private var spinner: Spinner? = null
    private var despesa : Despesa = Despesa()
    private var despesaEdit : Despesa? = null
    private var categoryIntAtual : Int = 0

    private var showCloseMenu : MutableLiveData<Boolean> = MutableLiveData()

    companion object {
        fun newInstance() = InsertEditDespesaFragment()
    }

    private lateinit var viewModel: InsertEditDespesaViewModel

    fun getShowCloseMenu() : LiveData<Boolean> {
        return this.showCloseMenu
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.insert_despesa_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(despesaEdit != null) {
            activity?.title = "Editar despesa"
            edt_description_despesa.setText(despesaEdit?.description)
            edt_value_despesa.setText(despesaEdit?.valeu_despesa.toString())
            btn_delete_despesa.visibility = View.VISIBLE

            showCloseMenu.value = true
        }
        else
        {
            activity?.title = "Nova despesa"
            btn_delete_despesa.visibility = View.GONE

            showCloseMenu.value = false
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InsertEditDespesaViewModel::class.java)

        setSpinner()

        viewModel.getSucesso().observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            ClearFields()
            if(despesaEdit != null){
                activity?.finish()
            }
        })
        viewModel.getFalha().observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        btn_salvar_despesa.setOnClickListener {
            insertEditDespesa()
        }

        btn_delete_despesa.setOnClickListener {
            showAlertDeleteDespesa()
        }
    }

    fun showAlertDeleteDespesa(){
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setMessage("Deseja deletar (${despesaEdit?.description}) da lista? Essa operação não pode ser desfeita.")
            ?.setPositiveButton("Sim",
                DialogInterface.OnClickListener { dialog, id ->
                    deleteDespesa()
                })
            ?.setNegativeButton("Cancelar",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
        builder?.create()
        builder?.show()
    }

    fun deleteDespesa(){
        if(despesaEdit != null) {
            viewModel.Delete(despesaEdit!!)
        }
    }

    fun insertEditDespesa(){
        despesa = Despesa()
        if(despesaEdit != null && despesaEdit?.id != 0L) {
            despesa.id = despesaEdit!!.id
            despesa.id_external = despesaEdit!!.id_external
            despesa.created_by = despesaEdit!!.created_by
            despesa.created_at = despesaEdit!!.created_at
        }

        despesa.category = categoryIntAtual
        despesa.description = edt_description_despesa.text.toString()
        despesa.valeu_despesa = edt_value_despesa.text.toString().toDouble()
        viewModel.InsertEditDespesa(despesa)
    }

    fun setDespesaToEdit(despesa: Despesa){
        this.despesaEdit = despesa
        despesaEdit?.category?.let { spinner?.setSelection(it, true) }
    }

    fun ClearFields(){
        edt_description_despesa.text.clear()
        edt_value_despesa.text.clear()
        spinner?.setSelection(0, true)
    }

    fun setSpinner(){

        val res = resources
        val listString = res.getStringArray(R.array.categorias).asList()

        spinner = spinner_category_despesa
        val adapter = CategorySpinnerAdapter(context!!, R.layout.item_spinner, listString)
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = selectItemCategoria

        if(despesaEdit != null){
            despesaEdit?.category?.let { spinner?.setSelection(it, true) }
        }
    }

    val selectItemCategoria = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            categoryIntAtual = pos
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            categoryIntAtual = 0
        }
    }
}
