package com.github.fernandospr.encryption.sample.rsa

import com.github.fernandospr.encryption.sample.common.TabLayoutActivity

class RSAActivity : TabLayoutActivity() {
    override val tabNameToFragmentLambdaList = listOf(
        "Generator" to { GeneratorFragment.newInstance() },
        "Encrypt" to { EncryptFragment.newInstance() },
        "Decrypt" to { DecryptFragment.newInstance() },
        "Sign" to { SignFragment.newInstance() },
        "Verify" to { SignatureVerificationFragment.newInstance() },
    )
}