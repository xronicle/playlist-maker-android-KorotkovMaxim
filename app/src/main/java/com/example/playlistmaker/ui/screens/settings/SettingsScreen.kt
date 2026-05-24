package com.example.playlistmaker.ui.screens.settings

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.components.ListItem
import com.example.playlistmaker.ui.components.OptionsList

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel, onBack: () -> Unit) {
    val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Назад",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBack() },
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.settings),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        val shareMessage = stringResource(R.string.share_app_message)
        val userAgreementUrl = stringResource(R.string.user_agreement_url)

        OptionsList(
            paddings = PaddingValues(0.dp),
            itemsPaddings = PaddingValues(vertical = 20.dp, horizontal = 12.dp),
            items = listOf(

                ListItem(
                    content = {
                        Text(
                            text = "Темная тема",
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    trailingIcon = {
                        Box(modifier = Modifier.height(24.dp), contentAlignment = Alignment.CenterEnd) {
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { isChecked ->
                                    settingsViewModel.updateTheme(isChecked)
                                },
                                modifier = Modifier.scale(0.8f),
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color.Gray
                                )
                            )
                        }
                    }
                ) {},

                ListItem(
                    content = {
                        Text(
                            text = stringResource(R.string.share_app),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
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
                    content = {
                        Text(
                            text = stringResource(R.string.contact_support),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
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
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = "mailto:".toUri()
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.support_mail_to)))
                        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.support_mail_title))
                        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.support_mail_text))
                    }
                    context.startActivity(intent)
                },

                ListItem(
                    content = {
                        Text(
                            text = stringResource(R.string.user_agreement),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
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