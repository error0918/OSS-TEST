# ___OSS-TEST___
##### Firebase와 Google Play OSS Service 서비스의 호환성에 대한 테스트


---

### 결과

오류 발생 부분
- Kotlin 라이선스 정보에서 17글자가 실제보다 적은 오차가 발생함
  임시 해결 방책
- Kotlin 이후의 라이선스를 추출할 떄는 인덱스에서 17을 뺌

---

### 다운로드

<div align=center>

[1.0.0](https://github.com/error0918/OSS-TEST/raw/master/APK/1.0.0/app-release.apk)

</div>

---

### 코드

``` Kotlin
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
                License.license[rawTitle.substring(spaceIndex + 1, rawTitle.length - 1)] =
                    try {
                        rawLicenses.substring(
                            if (License.license.keys.indexOf("Kotlin") != -1) range[0].toInt() - 17
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
```

---

<div align=center>

###### Copyright 2022. Error0918 (JTAEYEON) all rights reserved.

</div>
