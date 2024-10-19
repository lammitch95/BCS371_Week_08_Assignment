package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.farmingdale.pizzapartybottomnavbar.ui.theme.PizzaPartyBottomNavBarTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationGraph(navController: NavHostController, onBottomBarVisibilityChanged: (Boolean) -> Unit) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = "Pizza Icon") },
                    label = { Text("Pizza Order") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(BottomNavigationItems.PizzaScreen.route)
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "GPA App Icon") },
                    label = { Text("GPA App") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(BottomNavigationItems.GpaAppScreen.route)
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.AccountCircle, contentDescription = "Screen 3 Icon") },
                    label = { Text("Screen3") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(BottomNavigationItems.Screen3.route)
                    }
                )
            }
        },
        gesturesEnabled = true
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pizza Party App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open() // Open the drawer when the icon is clicked
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Open Drawer")
                        }
                    }
                )
            }
        ) { contentPadding ->

            NavHost(
                navController,
                startDestination = BottomNavigationItems.Welcome.route,
                modifier = Modifier.padding(contentPadding)
            ) {
                composable(BottomNavigationItems.Welcome.route) {
                    onBottomBarVisibilityChanged(false)
                    SplashScreen(navController = navController)
                }
                composable(BottomNavigationItems.PizzaScreen.route) {
                    onBottomBarVisibilityChanged(true)
                    PizzaPartyScreen()
                }
                composable(BottomNavigationItems.GpaAppScreen.route) {
                    onBottomBarVisibilityChanged(true)
                    GpaAppScreen()
                }
                composable(BottomNavigationItems.Screen3.route) {
                    onBottomBarVisibilityChanged(true)
                    Screen3()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNavigationGraph() {
    PizzaPartyBottomNavBarTheme {
        val navController = rememberNavController()
        NavigationGraph(navController = navController, onBottomBarVisibilityChanged = {})
    }
}


// ToDo 8: This is the homework:
// add a drawer navigation as described in drawable drawermenu.png
// Improve the design and integration of the app for 5 extra credit points.