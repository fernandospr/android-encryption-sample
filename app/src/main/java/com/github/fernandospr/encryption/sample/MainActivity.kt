package com.github.fernandospr.encryption.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.fernandospr.encryption.sample.aes.AESActivity
import com.github.fernandospr.encryption.sample.databinding.ActivityMainBinding
import com.github.fernandospr.encryption.sample.rsa.RSAActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAes.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AESActivity::class.java
                )
            )
        }
        binding.buttonRsa.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RSAActivity::class.java
                )
            )
        }
    }
}