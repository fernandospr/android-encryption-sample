package com.github.fernandospr.encryption.sample.rsa

import com.github.fernandospr.encryption.sample.utils.decodeBase64
import com.github.fernandospr.encryption.sample.utils.encodeToBase64
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.spec.MGF1ParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource

class RSA {

    val publicKey: PublicKey
    val privateKey: PrivateKey

    init {
        val keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM)
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.genKeyPair()

        publicKey = keyPair.public
        privateKey = keyPair.private
    }

    val base64EncodedPublicKey = publicKey.encoded.encodeToBase64()
    val base64EncodedPrivateKey = privateKey.encoded.encodeToBase64()

    companion object {
        private const val RSA_ALGORITHM = "RSA"
        private const val RSA_SIGNATURE_ALGORITHM = "SHA512withRSA"
        private const val RSA_TRANSFORMATION = "RSA/ECB/OAEPPadding"
        private val RSA_ALGORITHM_PARAMETER_SPEC = OAEPParameterSpec(
            "SHA-256",
            "MGF1",
            MGF1ParameterSpec.SHA256,
            PSource.PSpecified.DEFAULT
        )

        fun encrypt(data: ByteArray, publicKeyString: String): ByteArray {
            val publicKey = base64ToPublicKey(publicKeyString)

            val cipher = getRSACipher()
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, RSA_ALGORITHM_PARAMETER_SPEC)
            return cipher.doFinal(data)
        }

        fun decrypt(data: ByteArray, privateKeyString: String): ByteArray {
            val privateKey = base64ToPrivateKey(privateKeyString)

            val cipher = getRSACipher()
            cipher.init(Cipher.DECRYPT_MODE, privateKey, RSA_ALGORITHM_PARAMETER_SPEC)
            return cipher.doFinal(data)
        }

        fun sign(data: ByteArray, privateKeyString: String): ByteArray {
            val privateKey = base64ToPrivateKey(privateKeyString)

            val signer = getRSASignature().apply {
                initSign(privateKey)
                update(data)
            }
            return signer.sign()
        }

        fun verify(signature: ByteArray, data: ByteArray, publicKeyString: String): Boolean {
            val publicKey = base64ToPublicKey(publicKeyString)

            val signatureVerifier = getRSASignature().apply {
                initVerify(publicKey)
                update(data)
            }
            return signatureVerifier.verify(signature)
        }

        private fun base64ToPublicKey(publicKeyString: String): PublicKey {
            val publicKeyByteArray = publicKeyString.decodeBase64()
            return getRSAKeyFactory()
                .generatePublic(X509EncodedKeySpec(publicKeyByteArray))
        }

        private fun base64ToPrivateKey(privateKeyString: String): PrivateKey {
            val privateKeyByteArray = privateKeyString.decodeBase64()
            return getRSAKeyFactory()
                .generatePrivate(PKCS8EncodedKeySpec(privateKeyByteArray))
        }

        private fun getRSACipher() = Cipher.getInstance(RSA_TRANSFORMATION)

        private fun getRSASignature() = Signature.getInstance(RSA_SIGNATURE_ALGORITHM)

        private fun getRSAKeyFactory() = KeyFactory.getInstance(RSA_ALGORITHM)
    }
}
