package br.com.diegocruz.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.diegocruz.R

class DespesaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var img = itemView.findViewById<ImageView>(R.id.despesa_item_icon)
    var line = itemView.findViewById<View>(R.id.despesa_item_line)
    var date = itemView.findViewById<TextView>(R.id.despesa_item_date)
    var destription = itemView.findViewById<TextView>(R.id.despesa_descrition_txt)
    var time = itemView.findViewById<TextView>(R.id.despesa_item_time)
    var category = itemView.findViewById<TextView>(R.id.despesa_item_category_txt)
    var value = itemView.findViewById<TextView>(R.id.despesa_item_value)
    var view = itemView
}
