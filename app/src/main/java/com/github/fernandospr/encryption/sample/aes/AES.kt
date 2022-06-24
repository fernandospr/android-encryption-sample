package com.github.fernandospr.encryption.sample.aes

import com.github.fernandospr.encryption.sample.utils.decodeBase64
import com.github.fernandospr.encryption.sample.utils.encodeToBase64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AES {

    val key: SecretKey
    val ivParameterSpec: IvParameterSpec

    init {
        val keygen = KeyGenerator.getInstance(AES_ALGORITHM)
        keygen.init(256)
        key = keygen.generateKey()

        val ivRandom = SecureRandom()
        val iv = ByteArray(16)
        ivRandom.nextBytes(iv)
        ivParameterSpec = IvParameterSpec(iv)
    }

    val base64EncodedKey = key.encoded.encodeToBase64()
    val base64EncodedIv = ivParameterSpec.iv.encodeToBase64()

    companion object {
        private const val AES_ALGORITHM = "AES"
        private const val AES_TRANSFORMATION = "AES/CBC/PKCS5PADDING"

        fun encrypt(data: ByteArray, keyString: String, ivString: String? = null): EncryptedData {
            val key = base64ToSecretKeySpec(keyString)

            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
            if (ivString.isNullOrBlank()) {
                cipher.init(Cipher.ENCRYPT_MODE, key)
            } else {
                val ivParameterSpec = base64ToIvParameterSpec(ivString)
                cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec)
            }
            return EncryptedData(cipher.doFinal(data), cipher.iv)
        }

        fun decrypt(data: ByteArray, keyString: String, ivString: String): ByteArray {
            val key = base64ToSecretKeySpec(keyString)
            val ivParameterSpec = base64ToIvParameterSpec(ivString)

            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec)
            return cipher.doFinal(data)
        }

        private fun base64ToSecretKeySpec(keyString: String): SecretKeySpec {
            val keyByteArray = keyString.decodeBase64()
            return SecretKeySpec(keyByteArray, AES_ALGORITHM)
        }

        private fun base64ToIvParameterSpec(ivString: String): IvParameterSpec {
            val ivByteArray = ivString.decodeBase64()
            return IvParameterSpec(ivByteArray)
        }
    }

    class EncryptedData(val data: ByteArray, val iv: ByteArray)
}
