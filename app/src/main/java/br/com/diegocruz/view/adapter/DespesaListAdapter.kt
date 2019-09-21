package br.com.diegocruz.view.adapter

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.diegocruz.R
import br.com.diegocruz.extesions.formateDDMMAAAAString
import br.com.diegocruz.extesions.formateHHmmString
import br.com.diegocruz.model.Despesa

interface DespesaItem {
    fun onTapItemDespesa(despesa: Despesa)
    fun onLongTapItemDespesa(despesa: Despesa)
}

class DespesaListAdapter : RecyclerView.Adapter<DespesaViewHolder> {

    private var cxt: Context? = null
    private var despesas: List<Despesa> = ArrayList()
    private var count: Int = 0
    private var dataAtual : String = ""
    private var delegate : DespesaItem? = null

    constructor(context: Context) : super() {
        this.cxt = context
        Log.d("CRUZDATA", "==================================================")
    }

    fun setListener(despesaItem: DespesaItem){
        this.delegate = despesaItem
    }

    fun updateDespesaList(despesas: List<Despesa>) {
        this.despesas = despesas
        count = despesas.count()

        despesas.forEach {
            despesa ->
            val data = despesa.created_at.formateDDMMAAAAString()
            if (dataAtual !=  data) {
                dataAtual = data
                despesa.showDate = true
            }else{
                despesa.showDate = false
            }
        }

        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DespesaViewHolder {
        val v = LayoutInflater.from(cxt).inflate(R.layout.item_list_despesa, parent, false)

        return DespesaViewHolder(v)
    }

    override fun getItemCount(): Int {
        return this.count
    }

    override fun onBindViewHolder(holder: DespesaViewHolder, position: Int) {

        val despesa = despesas.get(position)
        val despesaDate = despesa.created_at

        holder.img.setImageDrawable(cxt?.getDrawable(getIcon(despesa.category)))

        holder.destription.text = despesa.description
        holder.category.text = getCategory(despesa.category)
        holder.time.text = despesaDate.formateHHmmString()
        holder.value.text = despesa.valeu_despesa.toString()

        if (despesa.showDate) {
            holder.date.text = despesa.created_at.formateDDMMAAAAString()
            holder.date.visibility = View.VISIBLE
        }else{
            holder.date.visibility = View.GONE
        }

        holder.view.setOnClickListener {
            delegate?.onTapItemDespesa(despesa)
        }
        holder.view.setOnLongClickListener {
            delegate?.onLongTapItemDespesa(despesa)
            return@setOnLongClickListener true
        }
    }

    fun getCategory(categoryId: Int) : String {

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
}