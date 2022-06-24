package com.github.fernandospr.encryption.sample.aes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.github.fernandospr.encryption.sample.R
import com.github.fernandospr.encryption.sample.common.ViewBindingBaseFragment
import com.github.fernandospr.encryption.sample.databinding.AesDecryptBinding
import com.github.fernandospr.encryption.sample.utils.decodeBase64
import com.github.fernandospr.encryption.sample.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class DecryptFragment : ViewBindingBaseFragment<AesDecryptBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = AesDecryptBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            textFieldMsg.isVisible = false
            editTextKey.doOnTextChanged { _, _, _, _ ->
                textFieldMsg.isVisible = false
            }
            editTextIv.doOnTextChanged { _, _, _, _ ->
                textFieldMsg.isVisible = false
            }
            editTextEncryptedMsg.doOnTextChanged { _, _, _, _ ->
                textFieldMsg.isVisible = false
            }
            buttonEncrypt.setOnClickListener {
                decrypt()
            }
        }
    }

    private fun decrypt() = binding.apply {
        buttonEncrypt.hideKeyboard()
        try {
            val keyString = editTextKey.text.toString()
            val ivString = editTextIv.text.toString()
            val base64EncryptedMessage = editTextEncryptedMsg.text.toString()
            val encryptedMessageByteArray = base64EncryptedMessage.decodeBase64()
            val decryptedMessageByteArray =
                AES.decrypt(encryptedMessageByteArray, keyString, ivString)
            val decryptedMessage = decryptedMessageByteArray.toString(Charsets.UTF_8)
            editTextMsg.setText(decryptedMessage)
            textFieldMsg.isVisible = true
        } catch (e: Exception) {
            Log.e("DecryptFragment", "Decryption error", e)
            Snackbar.make(root, "Decryption error", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
                .show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DecryptFragment()
    }
}