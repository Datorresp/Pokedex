package com.icesi.pokedex

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.icesi.pokedex.databinding.ActivityMainBinding
import com.icesi.pokedex.model.Trainer

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getIn.setOnClickListener {
            var trainerName = binding.trainerTxt.text.toString()
            authenticator(trainerName)
        }

        binding.registerBtn.setOnClickListener {
            var trainerName = binding.trainerTxt.text.toString()
            var bol: Boolean = createNewTrainer(trainerName)
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("trainerName", trainerName)
            startActivity(intent)
        }

    }

    private fun createNewTrainer(trainerName: String): Boolean {
//        val query = db.collection("trainers").whereEqualTo("trainerName", trainerName)
//
//        query.get().addOnCompleteListener { it ->
//            it.result!!.forEach {
//                val trainer = it.toObject(Trainer::class.java)
//                if (trainer.trainerName == trainerName) {
//                    val intent = Intent(this, HomeActivity::class.java)
//                    startActivity(intent)
//                }
//            }
//        }
//
//        while (!query.get().isComplete) {
//
//        }

        Log.e(">>>>", trainerName)

        val newTrainer = Trainer(trainerName, "")
        db.collection("trainers")
            .add(newTrainer)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        return true
    }

    private fun authenticator(trainerName: String) {
        val query = db.collection("trainers").whereEqualTo("trainerName", trainerName)
        query.get().addOnCompleteListener { it ->
            it.result!!.forEach {
                val trainer = it.toObject(Trainer::class.java)
                if (trainer.trainerName == trainerName) {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("trainerName", trainer.trainerName)
                    startActivity(intent)
                }
            }
            Toast.makeText(applicationContext, "You Are Not Registered", Toast.LENGTH_SHORT).show()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}