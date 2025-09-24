package com.example.book.presentation.homescreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.book.R // <- use your app R
import com.example.book.presentation.TabScreen.TabScreen
import com.example.book.presentation.navigation.Routes
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.book.common.BookCategoryModel
import com.example.book.common.BookModel
import com.example.book.presentation.AuthViewModel
import com.example.book.presentation.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController,   viewModel: ViewModel = hiltViewModel(),
               authViewModel: AuthViewModel = hiltViewModel()
               ) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val urlHandler = LocalUriHandler.current
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var categoryImageUrl by remember { mutableStateOf(TextFieldValue("")) }

    var showAddCategoryModal by remember { mutableStateOf(false) }

    val state = viewModel.state.value

    var showExitDialog by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }

    // Catch system back press
    BackHandler {
        showExitDialog = true
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .width(250.dp)
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    NavigationDrawerItem(label = {
                        Text("Home")
                    }, selected = true,   icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") },onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    })

                    HorizontalDivider()
                    NavigationDrawerItem(label = {
                        Text("Version 1.0")
                    }, selected = false,
                        icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Version 1.0") },
                        onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                            Toast.makeText(context, "Version 1.0", Toast.LENGTH_SHORT).show()
                    })

                    HorizontalDivider()
                    NavigationDrawerItem(label = {
                        Text("Contact Me")
                    }, selected = false,
                        icon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "Contact Me") },
                        onClick = {
                           urlHandler.openUri("https://github.com/bishnudev1")
                        })
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehaviour.nestedScrollConnection),
            floatingActionButton = {
                FloatingActionButton (
                    onClick = { showAddCategoryModal = true },
                    content = { Icon(Icons.Default.Add, contentDescription = "Add Category") }
                )
            },
            topBar = {
                TopAppBar(
                    title = {
                        Row (verticalAlignment = Alignment.CenterVertically){
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Book Library",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                                )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                           Icon(imageVector = Icons.Filled.Menu, contentDescription = "Open Drawer")
                        }
                    },
                    scrollBehavior = scrollBehaviour,
                    actions = {
                        IconButton(onClick = {
                            navHostController.navigate(Routes.InsertBook.route)
                        }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Book")
                        }
                        IconButton(
                            onClick = {
                                showDialog = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Logout"
                            )
                        }
                    }

                )
            }

        ){
            innerpadding ->
            Column (
                modifier = Modifier.padding(innerpadding).fillMaxSize()
            ){
                TabScreen(navHostController)

                if(showAddCategoryModal){
                    Dialog(
                        onDismissRequest = {
                            showAddCategoryModal = false
                        }
                    ) {
                        Card(
                            modifier = Modifier.padding(16.dp),
                            shape = MaterialTheme.shapes.medium

                        ) {
                            Column (    modifier = Modifier
                                .padding(16.dp) // space inside the card
                                .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(12.dp)){
                                Text("Add Category", modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.titleLarge)
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = name,
                                    onValueChange = { name = it },
                                    label = { Text("Category Name") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                OutlinedTextField(
                                    value = categoryImageUrl,
                                    onValueChange = { categoryImageUrl = it },
                                    label = { Text("Image URL") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = {
                                        if(name.text.isEmpty() || categoryImageUrl.text.isEmpty()){
                                            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                                            return@Button

                                        }
                                        val category = BookCategoryModel(
                                            name = name.text,
                                            categoryImageUrl = categoryImageUrl.text,
                                        )
                                        viewModel.insertBookCategory(category, context, navHostController)
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Submit Category")
                                }

                                if (state.isLoading) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                                }
                            }
                        }
                    }
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Logout") },
                        text = { Text("Are you sure you want to logout?") },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showDialog = false
                                    authViewModel.logoutUser(context, navHostController)
                                }
                            ) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDialog = false }
                            ) {
                                Text("No")
                            }
                        }
                    )
                }
                if (showExitDialog) {
                    AlertDialog(
                        onDismissRequest = { showExitDialog = false },
                        title = { Text("Exit App") },
                        text = { Text("Are you sure you want to quit the app?") },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showExitDialog = false
                                    // actually quit the app
                                    android.os.Process.killProcess(android.os.Process.myPid())
                                }
                            ) {
                                Text("Yes")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showExitDialog = false }) {
                                Text("No")
                            }
                        }
                    )
                }
            }
        }
    }
}
