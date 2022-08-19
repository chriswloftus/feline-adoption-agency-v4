package uk.ac.aber.dcs.cs31620.faa.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.faa.R
import uk.ac.aber.dcs.cs31620.faa.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.faa.ui.theme.FAATheme
/**
 * Creates the navigation drawer. Uses Material 2 since the Material 3
 * ModalNavigationDrawer is incompatible with the Material 2 Scaffold.
 * Current implementation has an image at the top and then three items.
 * @param navController To pass through the NavHostController since navigation is required
 * @param closeDrawer To pass in the close navigation drawer behaviour as a lambda.
 * By default has an empty lambda.
 * @author Chris Loftus
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageNavigationDrawer(
    navController: NavHostController,
    closeDrawer: () -> Unit = {}
) {

    // We are currently unable to use Material 3 ModalNavigationDrawer
    // with Material 3 Scaffold! The latter does not have a drawerContent
    // parameter forcing us to use Material 2 Scaffold.
    // Also, ModalNavigationDrawer does not play well
    // with Material 2 Scaffold (different DrawerState classes). We are however, able to use the
    // Material 3 NavigationDrawerItem, making it easier to
    // specify nav drawer clickable icons
    val items = listOf(
        Pair(
            Icons.Default.Login,
            stringResource(R.string.login)
        ),
        Pair(
            Icons.Default.Help,
            stringResource(R.string.help)
        ),
        Pair(
            Icons.Default.Feedback,
            stringResource(R.string.feedback)
        )
    )

    // Not sure if rememberSaveable or rember is what we
    // want since it stays selected when we close and
    // reopen the drawer. However...
    val selectedItem = rememberSaveable { mutableStateOf(0) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        // This probably doesn't add much, although we
        // have quite a bit of screen space, even in landscape
        // Anything larger and can lose the buttons in landscape
        Image(
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 16.dp, top = 16.dp),
            painter = painterResource(id = R.drawable.kitten_small),
            contentDescription = stringResource(R.string.small_kitten),
            contentScale = ContentScale.Crop
        )

        items.forEachIndexed { index, item ->
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = item.first,
                        contentDescription = item.second
                    )
                },
                label = { Text(item.second) },
                // I'm not sure that having a default selected makes sense
                selected = index == selectedItem.value,
                onClick = {
                    // Here we would navigate to appropriate page
                    // We'll just set as selected.
                    selectedItem.value = index

                    // Exercise solution. Just close the drawer
                    // and navigate
                    if (index == 0){
                        // If we don't do this the drawer will be hidden
                        // when we navigate to the login screen, however,
                        // the back button etc will take us back to the
                        // open drawer, which is not usual behaviour
                        closeDrawer()
                        navController.navigate(route = Screen.Login.route)
                    }
                },
                modifier = Modifier
                    .padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}

@Preview
@Composable
private fun MainPageNavigationDrawerPreview() {
    FAATheme(dynamicColor = false) {
        val navController = rememberNavController()
        MainPageNavigationDrawer(navController)
    }
}