package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViews()
    }

    private fun setUpViews(){
        title = "Authentication"

        signUpButton.setOnClickListener {
            if (editTextEmailAddress.text.isNotEmpty() && editTextPassword.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    editTextEmailAddress.text.toString(), editTextPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful){
                        showHome(it.result.user?.email ?: "", ProviderType.BASIC)
                    }
                    else {showAlert()}
                }
            }
        }

        logInButton.setOnClickListener {
            if (editTextEmailAddress.text.isNotEmpty() && editTextPassword.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    editTextEmailAddress.text.toString(), editTextPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful){
                        showHome(it.result.user?.email ?: "", ProviderType.BASIC)
                    }
                    else {showAlert()}
                }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Authentication Failed")
        builder.setPositiveButton("OK", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome (email: String, provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply{
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}