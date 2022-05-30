package com.icesi.pokedex.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.icesi.pokedex.R
import com.icesi.pokedex.ShowPokemonActivity
import com.icesi.pokedex.model.Pokemon
import com.icesi.pokedex.model.Trainer
import com.icesi.pokedex.viewHolder.PokemonViewHolder

class PokemonAdapter : RecyclerView.Adapter<PokemonViewHolder>(), ShowPokemonActivity.PokemonDrop{

    private var pokemons= ArrayList<Pokemon>()
    lateinit var trainer: Trainer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val row = inflater.inflate(R.layout.pokemonrow,parent,false)
        val pokemonView = PokemonViewHolder(row)
        return pokemonView
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.pokemon = pokemon
        holder.trainer = trainer
        holder.nameTxt.text = pokemon.name

    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    fun deletePokemons(){
        pokemons.clear()
    }

    fun addPokemon(pokemon: Pokemon){
        pokemons.add(pokemon)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removePokemon(pokemon: Pokemon){
        pokemons.remove(pokemon)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun dropPokemon(task: Task<QuerySnapshot>) {
        for(doc in task.result!!){
            val pk = doc.toObject(Pokemon::class.java)
            removePokemon(pk)
            notifyDataSetChanged()
        }
    }
}