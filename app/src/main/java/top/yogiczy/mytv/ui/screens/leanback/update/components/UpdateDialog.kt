package top.yogiczy.mytv.ui.screens.leanback.update.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import top.yogiczy.mytv.data.entities.GitRelease
import top.yogiczy.mytv.ui.theme.LeanbackTheme

@Composable
fun LeanbackUpdateDialog(
    modifier: Modifier = Modifier,
    showDialogProvider: () -> Boolean = { false },
    onDismissRequest: () -> Unit = {},
    releaseProvider: () -> GitRelease = { GitRelease() },
    onUpdateAndInstall: () -> Unit = {},
) {
    if (showDialogProvider()) {
        val release = releaseProvider()
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            confirmButton = {
                androidx.tv.material3.Button(
                    onClick = onUpdateAndInstall,
                    modifier = Modifier.focusRequester(focusRequester),
                ) {
                    androidx.tv.material3.Text(text = "立即更新")
                }
            },
            dismissButton = {
                androidx.tv.material3.Button(onClick = onDismissRequest) {
                    androidx.tv.material3.Text(text = "忽略")
                }
            },
            title = {
                androidx.tv.material3.Text(text = "新版本：v${release.version}")
            },
            text = {
                LazyColumn {
                    item {
                        androidx.tv.material3.Text(text = release.description)
                    }
                }
            }
        )
    }
}

@Preview(device = "id:Android TV (720p)")
@Composable
private fun LeanbackUpdateDialogPreview() {
    LeanbackTheme {
        LeanbackUpdateDialog(
            showDialogProvider = { true },
            releaseProvider = {
                GitRelease(
                    version = "1.0.0",
                    description = "版本更新日志".repeat(100),
                )
            }
        )
    }
}