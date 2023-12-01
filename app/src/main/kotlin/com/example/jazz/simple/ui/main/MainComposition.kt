package com.example.jazz.simple.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jazz.simple.R
import com.example.jazz.simple.ui.core.AlertDialogComposition
import com.example.jazz.simple.ui.main.MainViewModel.Mode.Conference
import com.example.jazz.simple.ui.main.MainViewModel.Mode.Loader
import com.example.jazz.simple.ui.main.MainViewModel.Mode.Lobby
import com.example.jazz.simple.ui.main.MainViewModel.Mode.SignIn
import com.example.jazz.simple.ui.main.MainViewModel.Mode.Stars
import com.example.jazz.simple.ui.main.MainViewModel.Mode.TerminateError
import com.example.jazz.simple.ui.main.MainViewModel.Mode.Webinar
import com.example.jazz.simple.ui.main.conference.ConferenceComposition
import com.example.jazz.simple.ui.main.error.ErrorComposition
import com.example.jazz.simple.ui.main.loader.LoaderComposition
import com.example.jazz.simple.ui.main.lobby.LobbyComposition
import com.example.jazz.simple.ui.main.signin.SignInComposition
import com.example.jazz.simple.ui.main.stars.StarsComposition
import com.example.jazz.simple.ui.main.webinar.WebinarComposition
import com.example.jazz.simple.ui.theme.JazzSimpleTheme

@Composable
fun MainComposition(vm: MainViewModel = viewModel()) {

    val state by vm.uiStateFlow.collectAsState()

    JazzSimpleTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

            BackHandler {
                vm.onBack()
            }

            when (state.mode) {
                is Conference -> ConferenceComposition()
                is TerminateError -> ErrorComposition((state.mode as TerminateError).reason)
                is Loader -> LoaderComposition()
                is Lobby -> LobbyComposition()
                is Webinar -> WebinarComposition()
                is SignIn -> SignInComposition()
                is Stars -> StarsComposition()
            }
        }

        if (state.isDeeplinkRequestVisible) {
            AlertDialogComposition(
                onDismissRequest = { vm.rejectDeeplink() },
                onConfirmation = { vm.acceptDeeplink() },
                dialogTitle = "Внимание!",
                dialogText = "В настоящий момент идет конференция, прервать ее и присоединиться к новой?",
                iconResId = R.drawable.ic_warning
            )
        }
    }
}