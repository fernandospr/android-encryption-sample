package com.github.fernandospr.encryption.sample.rsa

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
import com.github.fernandospr.encryption.sample.databinding.RsaSignatureVerificationBinding
import com.github.fernandospr.encryption.sample.utils.decodeBase64
import com.github.fernandospr.encryption.sample.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class SignatureVerificationFragment : ViewBindingBaseFragment<RsaSignatureVerificationBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RsaSignatureVerificationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imageViewVerificationResult.isVisible = false
            editTextPubKey.doOnTextChanged { _, _, _, _ ->
                imageViewVerificationResult.isVisible = false
            }
            editTextMsg.doOnTextChanged { _, _, _, _ ->
                imageViewVerificationResult.isVisible = false
            }
            editTextSignature.doOnTextChanged { _, _, _, _ ->
                imageViewVerificationResult.isVisible = false
            }
            buttonVerify.setOnClickListener {
                verify()
            }
        }
    }

    private fun verify() = binding.apply {
        buttonVerify.hideKeyboard()
        try {
            val publicKeyString = editTextPubKey.text.toString()
            val messageByteArray =
                editTextMsg.text.toString().toByteArray(Charsets.UTF_8)
            val base64Signature = editTextSignature.text.toString()
            val signatureByteArray = base64Signature.decodeBase64()
            val result = RSA.verify(signatureByteArray, messageByteArray, publicKeyString)
            imageViewVerificationResult.isVisible = true
            imageViewVerificationResult.setImageResource(
                if (result) {
                    R.drawable.ic_baseline_check_circle_24
                } else {
                    R.drawable.ic_baseline_error_24
                }
            )
        } catch (e: Exception) {
            Log.e("SignatureVerificationFragment", "Signature verification error", e)
            Snackbar.make(root, "Signature verification error", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
                .show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignatureVerificationFragment()
    }
}