package com.taeyeon.oss_test

import android.content.Context
import java.io.ByteArrayOutputStream

object License {
    var license = mutableMapOf<String, String>()

    fun initialize(context: Context) {
        val rawTitles: String
        val rawTitlesInputStream =
            context.resources.openRawResource(R.raw.third_party_license_metadata)
        val rawTitlesByteArrayOutputStream = ByteArrayOutputStream()
        var rawTitlesIndex: Int = rawTitlesInputStream.read()
        while (rawTitlesIndex != -1) {
            rawTitlesByteArrayOutputStream.write(rawTitlesIndex)
            rawTitlesIndex = rawTitlesInputStream.read()
        }
        rawTitles = String(rawTitlesByteArrayOutputStream.toByteArray(), Charsets.UTF_8)
        rawTitlesInputStream.close()

        val rawLicenses: String
        val rawLicensesInputStream = context.resources.openRawResource(R.raw.third_party_licenses)
        val rawLicensesByteArrayOutputStream = ByteArrayOutputStream()
        var rawLicensesIndex: Int = rawLicensesInputStream.read()
        while (rawLicensesIndex != -1) {
            rawLicensesByteArrayOutputStream.write(rawLicensesIndex)
            rawLicensesIndex = rawLicensesInputStream.read()
        }
        rawLicenses = String(rawLicensesByteArrayOutputStream.toByteArray(), Charsets.UTF_8)


        rawTitles.split("\n").forEach { rawTitle ->
            if (rawTitle.isNotEmpty()) {
                val spaceIndex = rawTitle.indexOf(" ")
                if (spaceIndex > 0) {
                    val range = rawTitle.substring(0, spaceIndex).split(":")
                    if (range.size == 2) {
                        license[rawTitle.substring(spaceIndex + 1, rawTitle.length - 1)] =
                            try {
                                rawLicenses.substring(
                                    if (license.keys.indexOf("Kotlin") != -1) range[0].toInt() - 17
                                            until range[0].toInt() + range[1].toInt() - 17
                                    else range[0].toInt() until range[0].toInt() + range[1].toInt())
                            } catch (exception: Exception) {
                                "Error :("
                            }
                    }
                }
            }
        }
        license = license.toSortedMap().toMutableMap()
    }
}