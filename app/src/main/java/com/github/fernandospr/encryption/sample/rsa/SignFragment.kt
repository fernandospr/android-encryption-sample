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
import com.github.fernandospr.encryption.sample.databinding.RsaSignBinding
import com.github.fernandospr.encryption.sample.utils.copyToClipboard
import com.github.fernandospr.encryption.sample.utils.encodeToBase64
import com.github.fernandospr.encryption.sample.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class SignFragment : ViewBindingBaseFragment<RsaSignBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RsaSignBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            layoutSignature.isVisible = false
            editTextPrvKey.doOnTextChanged { _, _, _, _ ->
                layoutSignature.isVisible = false
            }
            editTextMsg.doOnTextChanged { _, _, _, _ ->
                layoutSignature.isVisible = false
            }
            viewCopySignature.setOnClickListener {
                requireContext().copyToClipboard(editTextSignature.text.toString())
            }
            buttonSign.setOnClickListener {
                sign()
            }
        }
    }

    private fun sign() = binding.apply {
        buttonSign.hideKeyboard()
        try {
            val privateKeyString = editTextPrvKey.text.toString()
            val messageByteArray =
                editTextMsg.text.toString().toByteArray(Charsets.UTF_8)
            val signatureByteArray = RSA.sign(messageByteArray, privateKeyString)
            val signature = signatureByteArray.encodeToBase64()
            editTextSignature.setText(signature)
            layoutSignature.isVisible = true
        } catch (e: Exception) {
            Log.e("SignFragment", "Sign error", e)
            Snackbar.make(root, "Sign error", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
                .show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignFragment()
    }
}