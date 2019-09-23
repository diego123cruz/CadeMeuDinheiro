package br.com.diegocruz.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.diegocruz.R
import br.com.diegocruz.model.Despesa
import br.com.diegocruz.view.ui.insertdespesa.InsertEditDespesaFragment
import io.realm.Realm

class InsertEditDespesaActivity : AppCompatActivity() {

    private var showCloseMenu : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insert_despesa_activity)

        if (savedInstanceState == null) {

            val fragment = InsertEditDespesaFragment.newInstance()
            val despesaInt : Long = intent.getLongExtra("despesa_id", 0)
            if( despesaInt != 0L){
                val realm = Realm.getDefaultInstance()
                try {
                    val despesa = realm.where(Despesa::class.java).equalTo("id", despesaInt).findFirst()
                    if(despesa != null) {
                        fragment.setDespesaToEdit(realm.copyFromRealm(despesa))
                    }
                }
                finally {
                    realm.close()
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)

        if(fragment is InsertEditDespesaFragment){
            fragment.getShowCloseMenu().observe(this, Observer {
                showCloseMenu = it
                invalidateOptionsMenu()
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()

        if (showCloseMenu) {
            menuInflater.inflate(R.menu.close_menu, menu)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_close -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
