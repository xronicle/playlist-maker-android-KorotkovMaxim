package com.example.playlistmaker.ui.screens.settings

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.components.List
import com.example.playlistmaker.ui.components.ListItem
import com.example.playlistmaker.ui.components.TabTopBar
import androidx.core.net.toUri

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            topBar = { TabTopBar(stringResource(R.string.settings),onBack) }
        ) {
            paddingValues ->
            val shareMessage = stringResource(R.string.share_app_message)
            val userAgreementUrl = stringResource(R.string.user_agreement_url)
            List(
                textStyle = MaterialTheme.typography.bodyLarge,
                paddings = paddingValues,
                items = listOf(
                    ListItem(
                        text = stringResource(R.string.share_app),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.share),
                                contentDescription = stringResource(R.string.share),
                                modifier = Modifier
                                    .size(24.dp)
                                    .aspectRatio(1f),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    ) {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        intent.setType("text/plain")
                        context.startActivity(intent)
                    },
                    ListItem(
                        text = stringResource(R.string.contact_support),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.support),
                                contentDescription = stringResource(R.string.support),
                                modifier = Modifier
                                    .size(24.dp)
                                    .aspectRatio(1f),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    ) {
                        val intent = Intent(Intent.ACTION_SENDTO)
                        intent.data = "mailto:".toUri()
                        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("1eshiy@tpu.ru"))
                        intent.putExtra(Intent.EXTRA_TITLE, "Сообщение разработчикам и разработчицам приложения Playlist Maker")
                        intent.putExtra(Intent.EXTRA_TEXT, "Спасибо разработчикам и разработчицам за крутое приложение!")
                        context.startActivity(intent)
                    },
                    ListItem(
                        text = stringResource(R.string.user_agreement),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.chevron_right),
                                contentDescription = stringResource(R.string.chevron_right),
                                modifier = Modifier
                                    .size(24.dp)
                                    .aspectRatio(1f),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    ) {
                        val intent = Intent(Intent.ACTION_VIEW, userAgreementUrl.toUri())
                        context.startActivity(intent)
                    }
                )
            )
        }
    }
}