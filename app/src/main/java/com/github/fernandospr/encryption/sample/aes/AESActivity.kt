package com.github.fernandospr.encryption.sample.aes

import com.github.fernandospr.encryption.sample.common.TabLayoutActivity

class AESActivity : TabLayoutActivity() {
    override val tabNameToFragmentLambdaList = listOf(
        "Generator" to { GeneratorFragment.newInstance() },
        "Encrypt" to { EncryptFragment.newInstance() },
        "Decrypt" to { DecryptFragment.newInstance() },
    )
}