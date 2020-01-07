package com.example.tabapplication.ui.main.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.helper.GlideApp
import kotlinx.android.synthetic.main.item_dictionary.view.*
import kotlinx.android.synthetic.main.item_gallery_image.view.*
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientsAdapter(val iList: ArrayList<Ingredient>): RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {
    private var context: Context? = null
    var listener: ClickListener? =null
    override fun getItemCount(): Int {return iList.size}
    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {

        holder.ing_name.text = iList[position].ing_name
        holder.bind()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        context = parent.context

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_ingredient, parent,
            false)
        return IngredientViewHolder(view)

    }

    inner class IngredientViewHolder(root: View) : RecyclerView.ViewHolder(root) {
            val ing_name = root.ing_text_item
        fun bind() {
            val ing_image = iList[adapterPosition].ing_image
            GlideApp.with(context!!)
                .load(Uri.parse(ing_image))
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.ing_image_item)

                itemView.setOnClickListener {
                 listener?.onClick(adapterPosition)
            }
        }

    }
}

