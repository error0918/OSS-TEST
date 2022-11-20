@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.taeyeon.oss_test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.taeyeon.oss_test.ui.theme.OSSTESTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        License.initialize(this)
        setContent {
            OSSTESTTheme {
                Content()
                if (isResultDialog) ResultDialog()
                if (isLicenseDialog) LicenseDialog()
            }
        }
    }

    companion object {
        var isResultDialog by mutableStateOf(false)
        var isLicenseDialog by mutableStateOf(false)

        @Composable
        fun Content() {
            val context = LocalContext.current

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(32.dp)
                            )
                            .padding(32.dp)
                            .fillMaxWidth()
                    ) {
                        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "체크",
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "OSS 라이센스 테스트",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "Firebase와 Google Play OSS Service 서비스의 호환성에 대해 테스트합니다.",
                                style = MaterialTheme.typography.titleSmall,
                                textAlign = TextAlign.Center,
                                color = LocalContentColor.current.copy(alpha = 0.4f)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    shape = RoundedCornerShape(
                                        topStart = 20.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 20.dp,
                                        bottomEnd = 0.dp
                                    ),
                                    color = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                    onClick = {
                                        val intent = Intent(context, OssLicensesMenuActivity::class.java)
                                        context.startActivity(intent)
                                    }
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "OSS Activity",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .padding(vertical = 4.dp)
                                        .fillMaxHeight()
                                        .background(
                                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f),
                                            shape = CircleShape
                                        )
                                )
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    shape = RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 20.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 20.dp
                                    ),
                                    color = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                    onClick = { isLicenseDialog = true }
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "License Dialog",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    isResultDialog = true
                                }
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = "결과",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "결과",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Text(
                        text = "TEST TEXT: 1818",
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalContentColor.current.copy(alpha = 0.4f),
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                    Text(
                        text = "Copyright 2022. error0918 (JTAEYEON) all rights reserved.",
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalContentColor.current.copy(alpha = 0.4f),
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
            rememberSystemUiController().setSystemBarsColor(
                color = MaterialTheme.colorScheme.primary,
                darkIcons = isSystemInDarkTheme()
            )
        }

        @Composable
        fun ResultDialog() {
            Dialog(
                onDismissRequest = { isResultDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "결과",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = """
                                오류 발생 부분
                                 - Kotlin 라이선스 정보에서 17글자가 실제보다 적은 오차가 발생함
                                임시 해결 방책
                                 - Kotlin 이후의 라이선스를 추출할 떄는 인덱스에서 17을 뺌
                            """.trimIndent(),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .align(Alignment.End),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TextButton(onClick = { isResultDialog = false }) {
                                Text(
                                    text = "닫기",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }
            }
        }

        @Composable
        fun LicenseDialog() {
            Dialog(
                onDismissRequest = { isLicenseDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "License Dialog",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            License.license.forEach { (title, content) ->
                                item {
                                    var isExpanded by rememberSaveable { mutableStateOf(false) }
                                    TextButton(
                                        onClick = { isExpanded = !isExpanded },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RectangleShape,
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = title,
                                                style = MaterialTheme.typography.bodySmall,
                                                modifier = Modifier
                                                    .align(Alignment.CenterStart)
                                                    .padding(
                                                        end = LocalDensity.current.run {
                                                            MaterialTheme.typography.labelMedium.fontSize
                                                                .toPx()
                                                                .toDp()
                                                        }
                                                                + ButtonDefaults.TextButtonContentPadding.calculateTopPadding()
                                                                + ButtonDefaults.TextButtonContentPadding.calculateBottomPadding()
                                                    )
                                            )
                                            Icon(
                                                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                                contentDescription = if (isExpanded) "닫기" else "열기",
                                                tint = MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier
                                                    .size(
                                                        LocalDensity.current.run {
                                                            MaterialTheme.typography.labelMedium.fontSize
                                                                .toPx()
                                                                .toDp()
                                                        }
                                                                + ButtonDefaults.TextButtonContentPadding.calculateTopPadding()
                                                                + ButtonDefaults.TextButtonContentPadding.calculateBottomPadding()
                                                    )
                                                    .align(Alignment.CenterEnd)
                                            )
                                        }
                                    }
                                    Surface(
                                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(9.dp),
                                        contentColor = MaterialTheme.colorScheme.onSurface,
                                        shape = RoundedCornerShape(16.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        AnimatedVisibility(visible = isExpanded) {
                                            Text(
                                                text = content,
                                                style = MaterialTheme.typography.bodySmall,
                                                modifier = Modifier.padding(16.dp)
                                            )
                                        }
                                    }
                                    Divider(
                                        thickness = 1.dp,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .align(Alignment.End),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TextButton(onClick = { isLicenseDialog = false }) {
                                Text(
                                    text = "닫기",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}