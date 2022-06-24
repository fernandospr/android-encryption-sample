package com.github.fernandospr.encryption.sample.utils

import android.util.Base64

fun ByteArray.encodeToBase64() = Base64.encodeToString(this, Base64.NO_WRAP)

fun String.decodeBase64() = Base64.decode(this, Base64.NO_WRAP)