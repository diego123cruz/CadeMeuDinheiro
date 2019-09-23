package br.com.diegocruz.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import br.com.diegocruz.R
import br.com.diegocruz.getIcon


class CategorySpinnerAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {
    private var mContext: Context = context
    private var items: List<String> = objects
    private val mResource: Int = resource

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(mContext).inflate(mResource, parent, false)

        val imgIcon = view.findViewById<ImageView>(R.id.spinner_category_img)
        val txt =view.findViewById<TextView>(R.id.spinner_category_txt)

        txt.setText(items.get(position))

        imgIcon.setImageDrawable(mContext.getDrawable(getIcon(position)))

        return view
    }
}