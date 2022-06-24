package com.github.fernandospr.encryption.sample.aes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fernandospr.encryption.sample.common.ViewBindingBaseFragment
import com.github.fernandospr.encryption.sample.databinding.AesGeneratorBinding
import com.github.fernandospr.encryption.sample.utils.copyToClipboard

class GeneratorFragment : ViewBindingBaseFragment<AesGeneratorBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = AesGeneratorBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewCopyKey.setOnClickListener {
                requireContext().copyToClipboard(editTextKey.text.toString())
            }
            viewCopyIv.setOnClickListener {
                requireContext().copyToClipboard(editTextIv.text.toString())
            }
            buttonGenerate.setOnClickListener {
                generate()
            }
        }
    }

    private fun generate() = binding.apply {
        val aes = AES()
        editTextKey.setText(aes.base64EncodedKey)
        editTextIv.setText(aes.base64EncodedIv)
    }

    companion object {
        @JvmStatic
        fun newInstance() = GeneratorFragment()
    }
}