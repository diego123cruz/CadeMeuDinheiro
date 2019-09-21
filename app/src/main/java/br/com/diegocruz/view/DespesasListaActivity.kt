package br.com.diegocruz.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.diegocruz.R
import br.com.diegocruz.view.ui.despesaslista.DespesasListaFragment

class DespesasListaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.despesas_lista_activity)

        title = "Lista de despesas"

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DespesasListaFragment.newInstance())
                .commitNow()
        }
    }

}
