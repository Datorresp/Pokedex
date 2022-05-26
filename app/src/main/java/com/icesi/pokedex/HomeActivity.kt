package com.icesi.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.icesi.pokedex.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- Catch pokemon ---
        binding.catchPokemon.setOnClickListener {
            val pokemonName = binding.pokemonTxt.text.toString()
            if (pokemonName.isNotBlank()) {
                //save on db
            }else {
                Toast.makeText(applicationContext, "Need To Introduce Text", Toast.LENGTH_SHORT)
            }
        }

        // --- Show Pokemon ---
        binding.showPokemon.setOnClickListener {
            val pokemonName = binding.pokemonTxt.text.toString()
            if (pokemonName.isNotBlank()) {
                val intent = Intent(this, ShowPokemonActivity::class.java)
                intent.putExtra("pokemonName", pokemonName)
                startActivity(intent)
            }else {
                Toast.makeText(applicationContext, "Need To Introduce Text", Toast.LENGTH_SHORT)
            }
        }
    }
}