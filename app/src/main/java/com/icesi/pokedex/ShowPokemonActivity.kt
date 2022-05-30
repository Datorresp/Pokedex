package com.icesi.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.icesi.pokedex.databinding.ActivityShowPokemonBinding
import com.icesi.pokedex.model.Pokemon
import com.icesi.pokedex.model.Trainer

class ShowPokemonActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowPokemonBinding

    private val db = Firebase.firestore

    var pokemon: Pokemon? = null
    var trainer: Trainer? = null

    var listenerDrop: PokemonDrop? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle = intent.extras
        pokemon = intent.extras?.get("pokemon") as Pokemon
        trainer = intent.extras?.get("trainer") as Trainer

        //val list = searchPokemon(pokemon)

        binding.attackTxt.text = pokemon?.attack.toString()
        binding.defenseTxt.text = pokemon?.defense.toString()
        binding.velocityTxt.text = pokemon?.velocity.toString()
        binding.lifeTxt.text = pokemon?.life.toString()
        binding.nameTxt.text = pokemon?.name.toString().uppercase()


        //binding.nameTxt.text = list[0]
        //binding.defenseTxt.text = list[1]
        //binding.attackTxt.text = list[2]
        //binding.velocityTxt.text = list[3]
        //binding.lifeTxt.text = list[4]
        //Image binding.pokemonImage

        binding.catchPokemonBtn.setOnClickListener {
            // save on db
        }

    }

    private fun searchPokemon(pokemon: Pokemon?): List<String> {

        // list [name][Defense][Attack][Velocity][Life]
        return emptyList()
    }


    interface PokemonDrop{
        fun dropPokemon(task: Task<QuerySnapshot>)
    }
}