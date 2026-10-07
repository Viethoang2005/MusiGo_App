package com.example.musigo.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import com.example.musigo.component.BottomNavItem
import com.example.musigo.component.BottomNavigationBar
import com.example.musigo.component.MiniPlayer
import com.example.musigo.ui.auth.LoginScreen
import com.example.musigo.ui.home.HomeScreen
import com.example.musigo.ui.library.LibraryScreen
import com.example.musigo.component.PlayerScreen
import com.example.musigo.ui.profile.ProfileScreen
import com.example.musigo.ui.search.SearchScreen
import com.example.musigo.ui.song.SongViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val songViewModel: SongViewModel = hiltViewModel()

    val playerState by songViewModel.playerState.collectAsState()
    val currentSong = playerState.currentSong
    val isPlaying = playerState.isPlaying

    val bottomNavRoutes = listOf(
        BottomNavItem.Home.route,
        BottomNavItem.Search.route,
        BottomNavItem.Library.route,
        BottomNavItem.Profile.route
    )

    val showMainUI = currentRoute in bottomNavRoutes
    Scaffold(
        bottomBar = {
            if(showMainUI) {
                Column{
                    MiniPlayer(
                        song = currentSong,
                        isPlaying = isPlaying,
                        onNavigateToPlayerScreen = {
                            navController.navigate(Routes.Player)
                        },
                        onNext = { songViewModel.playNext() },
                        onPlayPauseClick = {
                            songViewModel.togglePlayPause()
                        }
                    )
                    BottomNavigationBar(
                        navController = navController
                    )
                }
            }

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    songViewModel = songViewModel
                )
            }

            composable(BottomNavItem.Search.route) {
                SearchScreen(
                    songViewModel = songViewModel
                )
            }

            composable(BottomNavItem.Library.route) {
                LibraryScreen()
            }

            composable(BottomNavItem.Profile.route) {
                ProfileScreen()
            }

            composable(Routes.Login) {
                LoginScreen()
            }

            composable(Routes.Player) {
                PlayerScreen(
                    song = currentSong,
                    songViewModel = songViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}