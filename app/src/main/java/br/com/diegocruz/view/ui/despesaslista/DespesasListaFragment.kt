package br.com.diegocruz.view.ui.despesaslista

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.diegocruz.R
import br.com.diegocruz.model.Despesa
import br.com.diegocruz.view.DespesaFragment
import br.com.diegocruz.view.MenuNovoFragment
import br.com.diegocruz.view.adapter.DespesaItem
import br.com.diegocruz.view.adapter.DespesaListAdapter
import kotlinx.android.synthetic.main.despesas_lista_fragment.*

class DespesasListaFragment : Fragment() {

    companion object {
        fun newInstance() = DespesasListaFragment()
    }

    private lateinit var viewModel: DespesasListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.despesas_lista_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DespesasListaViewModel::class.java)
        viewModel.getDespesasList().observe(this, Observer {
            despesas ->

            val adapter = DespesaListAdapter(context!!)
            adapter.setListener(listenerItemTap)
            despesa_list_recyclerview.adapter = adapter
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            despesa_list_recyclerview.layoutManager = layoutManager

            adapter.updateDespesaList(despesas)

        })
        viewModel.getDespesas()

    }

    val listenerItemTap = object : DespesaItem{
        override fun onLongTapItemDespesa(despesa: Despesa) {

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            val fragment = DespesaFragment()
            fragment.setDespesa(despesa)
            fragmentTransaction?.replace(R.id.container, fragment)
            fragmentTransaction?.commit()
        }

        override fun onTapItemDespesa(despesa: Despesa) {

        }

    }

}
