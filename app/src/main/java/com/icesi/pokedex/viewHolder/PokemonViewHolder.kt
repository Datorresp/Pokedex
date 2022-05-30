package com.icesi.pokedex.viewHolder

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.icesi.pokedex.R
import com.icesi.pokedex.ShowPokemonActivity
import com.icesi.pokedex.model.Pokemon
import com.icesi.pokedex.model.Trainer

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var pokemon: Pokemon? = null
    var trainer: Trainer? = null


    var pokemonImage: ImageView = itemView.findViewById(R.id.pokemonImage);
    var nameTxt: TextView = itemView.findViewById(R.id.nameTxt);
    var pokeBackBtn: Button = itemView.findViewById(R.id.pokeBackBtn)

    init{
        pokeBackBtn.setOnClickListener {
            val intent = Intent(itemView.context, ShowPokemonActivity::class.java).apply {

            }
            itemView.context.startActivity(intent)
        }
    }

}