package com.example.playlistmaker.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.components.TrackListItem

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    isDarkTheme: Boolean,
    onClick: (Int?) -> Unit

) {

    val screenState by searchViewModel.searchScreenState.collectAsState()

    var text by rememberSaveable { mutableStateOf("") }

    var isFocused by remember { mutableStateOf(false) }

    var historyList by remember { mutableStateOf<List<String>>(emptyList()) }


    val focusRequester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current


    LaunchedEffect(text) {

        searchViewModel.updateQuery(text)

    }

    LaunchedEffect(Unit) {

        historyList = searchViewModel.getHistoryList()

    }


    Column(

        modifier = Modifier

            .fillMaxSize()

            .background(MaterialTheme.colorScheme.background)

            .padding(top = 16.dp)

    ) {

        Row(

            modifier = Modifier

                .fillMaxWidth()

                .padding(horizontal = 16.dp, vertical = 12.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            Icon(

                imageVector = Icons.AutoMirrored.Filled.ArrowBack,

                contentDescription = stringResource(R.string.Back),

                modifier = Modifier

                    .size(24.dp)

                    .clickable { onClick(null) },

                tint = MaterialTheme.colorScheme.onBackground

            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(

                text = stringResource(R.string.search),

                fontSize = 22.sp,

                fontWeight = FontWeight.Medium,

                color = MaterialTheme.colorScheme.onBackground

            )

        }

        TextField(

            value = text,

            onValueChange = { text = it },

            modifier = Modifier

                .fillMaxWidth()

                .padding(horizontal = 16.dp, vertical = 8.dp)

                .focusRequester(focusRequester)

                .onFocusChanged { isFocused = it.isFocused },

            placeholder = { Text(stringResource(R.string.search), color = Color.Gray) },

            leadingIcon = {

                Icon(
                    Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search),
                    tint = Color.Gray
                )

            },

            trailingIcon = {

                if (text.isNotEmpty()) {

                    Icon(

                        imageVector = Icons.Filled.Clear,

                        contentDescription = stringResource(R.string.clear),

                        tint = Color.Gray,

                        modifier = Modifier.clickable {

                            text = ""

                            searchViewModel.clearSearch()

                            focusManager.clearFocus()

                        }

                    )

                }

            },

            singleLine = true,

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),

            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),

            shape = RoundedCornerShape(8.dp),

            colors = TextFieldDefaults.colors(

                focusedTextColor = MaterialTheme.colorScheme.onBackground,

                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,

                focusedContainerColor = Color(0xFFE6E8EB).copy(alpha = 0.3f),

                unfocusedContainerColor = Color(0xFFE6E8EB).copy(alpha = 0.3f),

                cursorColor = Color.Blue,

                focusedIndicatorColor = Color.Transparent,

                unfocusedIndicatorColor = Color.Transparent

            )

        )


        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            when (screenState) {
                is SearchState.Initial -> {
                    if (text.isEmpty() && isFocused && historyList.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.history_search),
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
                            )

                            LazyColumn(modifier = Modifier.weight(1f)) {
                                items(historyList.size) { index ->
                                    Text(
                                        text = historyList[index],
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                text = historyList[index]
                                                focusManager.clearFocus()
                                            }
                                            .padding(horizontal = 16.dp, vertical = 12.dp)
                                    )
                                }
                            }

                            Button(
                                onClick = {
                                    historyList = emptyList()
                                },
                                modifier = Modifier.padding(bottom = 24.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Text(
                                    stringResource(R.string.clear_history),
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    }
                }

                is SearchState.Searching -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 100.dp),
                        color = Color.Blue
                    )
                }

                is SearchState.Success -> {
                    val tracks = (screenState as SearchState.Success).foundList
                    if (tracks.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 100.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val nothingFoundIcon =
                                if (isDarkTheme) R.drawable.nothing_dark else R.drawable.nothing_light

                            Image(
                                painter = painterResource(id = nothingFoundIcon),
                                contentDescription = stringResource(R.string.nothingfound),
                                modifier = Modifier.size(120.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.nothingfound),
                                fontSize = 19.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn {
                            items(tracks.size) { index ->
                                TrackListItem(track = tracks[index]) {
                                    onClick(index)
                                }
                            }
                        }
                    }
                }

                is SearchState.Fail -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val noInternetIcon =
                            if (isDarkTheme) R.drawable.nointernet_dark else R.drawable.nointernet_light

                        Image(
                            painter = painterResource(id = noInternetIcon),
                            contentDescription = stringResource(R.string.nointernet),
                            modifier = Modifier.size(120.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.error_internetconnection),
                            fontSize = 19.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { searchViewModel.updateQuery(text) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Text(
                                stringResource(R.string.refresh),
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}