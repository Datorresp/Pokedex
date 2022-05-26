package com.icesi.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.icesi.pokedex.databinding.ActivityShowPokemonBinding

class ShowPokemonActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowPokemonBinding

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle = intent.extras
        var pokemon = bundle?.getString("pokemonName")

        val list = searchPokemon(pokemon)

        binding.nameTxt.text = list[0]
        binding.defenseTxt.text = list[1]
        binding.attackTxt.text = list[2]
        binding.velocityTxt.text = list[3]
        binding.lifeTxt.text = list[4]
        //Image binding.pokemonImage

        binding.catchPokemonBtn.setOnClickListener {
            // save on db
        }

    }

    private fun searchPokemon(pokemon: String?): List<String> {

        // list [name][Defense][Attack][Velocity][Life]
        return emptyList()
    }


}