package com.icesi.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.icesi.pokedex.adapter.PokemonAdapter
import com.icesi.pokedex.api.Constant
import com.icesi.pokedex.databinding.ActivityHomeBinding
import com.icesi.pokedex.model.Pokemon
import com.icesi.pokedex.model.Trainer
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection
import androidx.lifecycle.*;
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    private lateinit var trainer: Trainer
    private lateinit var pokemon: Pokemon
    private var adapter = PokemonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pokemonRecycler = binding.pokedex
        pokemonRecycler.setHasFixedSize(true)
        pokemonRecycler.layoutManager = LinearLayoutManager(this)
        pokemonRecycler.adapter = adapter
        setContentView(binding.root)


        trainer = intent.extras?.get("trainer") as Trainer
        adapter.trainer = trainer
        var pokemon: Pokemon? = null

        Firebase.firestore.collection("users").document(trainer.trainerName).collection("pokemones")
            .orderBy("name", Query.Direction.DESCENDING)
            .get().addOnCompleteListener{ task ->
                for (doc in task.result!!){
                    val pk = doc.toObject(Pokemon::class.java)
                    adapter.addPokemon(pk)
                    adapter.notifyDataSetChanged()
                }
            }

        // --- Catch pokemon ---
        binding.catchPokemon.setOnClickListener {
            if (pokemon != null) {
                Firebase.firestore.collection("users").document(trainer.trainerName).collection("pokemones").document(pokemon.id).set(pokemon)
            }
            val intent = Intent(this, HomeActivity::class.java).apply {
            }
            startActivity(intent)
            finish()
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

    fun GETRequest(namePk: String, show: Boolean){

            val url = URL("${Constant.POKE_API}/pokemon/${namePk}")
            val client = url.openConnection() as HttpsURLConnection
            client.requestMethod = "GET"
            try {
                val json = client.inputStream.bufferedReader().readText()
                val jsonObject = JSONObject(json)
                val name = jsonObject.optJSONObject("species")?.optString("name")
                val type = jsonObject.optJSONArray("types")?.getJSONObject(0)?.optJSONObject("type")
                    ?.optString("name")
                val img = jsonObject.optJSONObject("sprites")?.optString("front_default")
                val stat = jsonObject.optJSONArray("stats")
                val life = stat?.getJSONObject(0)?.optInt("base_stat")
                val attack = stat?.getJSONObject(1)?.optInt("base_stat")
                val defense = stat?.getJSONObject(2)?.optInt("base_stat")
                val speed = stat?.getJSONObject(5)?.optInt("base_stat")

                pokemon = Pokemon(
                    UUID.randomUUID().toString(),
                     name!!,
                    "${defense!!}", "${attack!!}",
                    "${speed!!}", "${life!!}")
                if(show){
                    showPokemon()
                }else{
                    catchPokemon()
                }
            } catch (e: FileNotFoundException){

                    Toast.makeText(this@HomeActivity,"Non existent pokemon",Toast.LENGTH_LONG).show()

            }


    }

    private fun catchPokemon(){

            Firebase.firestore.collection("users").document(trainer.trainerName).collection("pokemones")
                .document(pokemon.id).set(pokemon)
            Firebase.firestore.collection("users").document(trainer.trainerName).collection("pokemones")
                .orderBy("dateCatch", Query.Direction.DESCENDING)
                .get().addOnCompleteListener{ task ->
                    adapter.deletePokemons()
                    for (doc in task.result!!){
                        val pk = doc.toObject(Pokemon::class.java)
                        adapter.addPokemon(pk)
                        adapter.notifyDataSetChanged()
                    }
                }

    }

    private fun showPokemon() {
        val intent = Intent(this@HomeActivity, ShowPokemonActivity::class.java).apply {

        }.apply {
            startActivity(this)
        }
    }

    private fun searchPokemon(namePk:String){

            val query = Firebase.firestore.collection("users").document(trainer.trainerName).collection("pokemones").whereEqualTo("name",namePk)
            query.get().addOnCompleteListener { task ->
                if (task.result?.size()!=0){
                    lateinit var pokemonSearch : Pokemon
                    adapter.deletePokemons()
                    for (document in task.result!!){
                        pokemonSearch = document.toObject(Pokemon::class.java)
                        adapter.addPokemon(pokemonSearch)
                        adapter.notifyDataSetChanged()
                        break
                    }
                }else{
                    Toast.makeText(this@HomeActivity,"Empty field or pokemon not catched",Toast.LENGTH_LONG).show()
                }
            }

    }

}