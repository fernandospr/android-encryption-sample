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
import com.github.fernandospr.encryption.sample.databinding.AesEncryptBinding
import com.github.fernandospr.encryption.sample.utils.copyToClipboard
import com.github.fernandospr.encryption.sample.utils.encodeToBase64
import com.github.fernandospr.encryption.sample.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class EncryptFragment : ViewBindingBaseFragment<AesEncryptBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = AesEncryptBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            layoutEncrypted.isVisible = false
            editTextKey.doOnTextChanged { _, _, _, _ ->
                layoutEncrypted.isVisible = false
            }
            editTextIv.doOnTextChanged { _, _, _, _ ->
                layoutEncrypted.isVisible = false
            }
            editTextMsg.doOnTextChanged { _, _, _, _ ->
                layoutEncrypted.isVisible = false
            }
            viewCopyEncryptedMsg.setOnClickListener {
                requireContext().copyToClipboard(editTextEncryptedMsg.text.toString())
            }
            viewCopyIv.setOnClickListener {
                requireContext().copyToClipboard(editTextIvAfter.text.toString())
            }
            buttonEncrypt.setOnClickListener {
                encrypt()
            }
        }
    }

    private fun encrypt() = binding.apply {
        buttonEncrypt.hideKeyboard()
        try {
            val keyString = editTextKey.text.toString()
            val ivString = editTextIv.text.toString()
            val messageByteArray =
                editTextMsg.text.toString().toByteArray(Charsets.UTF_8)

            val encrypted = AES.encrypt(messageByteArray, keyString, ivString)
            val encryptedMessage = encrypted.data.encodeToBase64()
            val iv = encrypted.iv.encodeToBase64()
            editTextEncryptedMsg.setText(encryptedMessage)
            editTextIvAfter.setText(iv)
            layoutEncrypted.isVisible = true
        } catch (e: Exception) {
            Log.e("EncryptFragment", "Encryption error", e)
            Snackbar.make(root, "Encryption error", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
                .show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EncryptFragment()
    }
}