package com.github.fernandospr.encryption.sample.rsa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fernandospr.encryption.sample.common.ViewBindingBaseFragment
import com.github.fernandospr.encryption.sample.databinding.RsaGeneratorBinding
import com.github.fernandospr.encryption.sample.utils.copyToClipboard

class GeneratorFragment : ViewBindingBaseFragment<RsaGeneratorBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RsaGeneratorBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewCopyPubKey.setOnClickListener {
                requireContext().copyToClipboard(editTextPubKey.text.toString())
            }
            viewCopyPrvKey.setOnClickListener {
                requireContext().copyToClipboard(editTextPrvKey.text.toString())
            }
            buttonGenerate.setOnClickListener {
                generate()
            }
        }
    }

    private fun generate() = binding.apply {
        val rsa = RSA()
        editTextPubKey.setText(rsa.base64EncodedPublicKey)
        editTextPrvKey.setText(rsa.base64EncodedPrivateKey)
    }

    companion object {
        @JvmStatic
        fun newInstance() = GeneratorFragment()
    }
}