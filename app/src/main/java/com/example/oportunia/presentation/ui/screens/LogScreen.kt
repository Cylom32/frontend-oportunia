package com.example.oportunia.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.sp
import com.example.oportunia.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.PasswordLabel
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel
import kotlinx.coroutines.launch


@Composable
fun LogScreen(
    navController: NavHostController,
    usersViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel
) {

    val token by usersViewModel.token.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColorsBlue,
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            color = Color.Transparent
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
            ) {
                // Primer Box arriba
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {


                        AsyncImage(
                            model = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/2048px-Instagram_logo_2022.svg.png",
                            contentDescription = "Logo handshake",
                            modifier = Modifier
                                .size(150.dp)
                                .padding(top = 20.dp)
                        )


                        Spacer(modifier = Modifier.height(50.dp))

                        Text(
                            text = stringResource(id = R.string.app_name),
                            fontSize = 64.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Segundo Box abajo
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(start = 60.dp, top = 120.dp, end = 60.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(
                            text = stringResource(id = R.string.emailL),
                            fontSize = 17.sp,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp)
                        )

                        Box(
                            modifier = Modifier
                                .shadow(6.dp, RoundedCornerShape(10.dp), clip = true)
                                .background(Color.White, RoundedCornerShape(10.dp))
                        ) {
                            com.example.oportunia.presentation.ui.components.Label(
                                email = email,
                                onEmailChange = { email = it },
                                ""
                            )
                        }

                        Text(
                            text = stringResource(id = R.string.passworL),
                            fontSize = 17.sp,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 2.dp)
                        )

                        Box(
                            modifier = Modifier
                                .shadow(6.dp, RoundedCornerShape(10.dp), clip = true)
                                .background(Color.White, RoundedCornerShape(10.dp))
                        ) {
                            PasswordLabel(
                                value = password,
                                onValueChange = { password = it },
                                ""
                            )
                        }
                    }
                }

                // Tercer Box (Login y enlaces)
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(start = 80.dp, end = 80.dp),
                    contentAlignment = Alignment.TopCenter
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Login",
                            fontSize = 24.sp,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .clickable {


                                    coroutineScope.launch {
                                        // En tu Composable o función donde haces login:

                                        if (email.isNotEmpty() && password.isNotEmpty()) {
                                            usersViewModel.login(email, password) { isValid ->
                                                if (isValid) {
                                                    usersViewModel.fetchUserByEmail(email) { userId ->
                                                        if (userId != null) {
                                                            val token =
                                                                usersViewModel.token.value.orEmpty()
                                                            Log.d("LoginDebug", "Token actual: $token")
                                                            Log.d(
                                                                "LoginDebug",
                                                                "llamando a student viewmodel: $token"
                                                            )
                                                            studentViewModel.fetchStudentByUserId(
                                                                token,
                                                                userId
                                                            ) { found ->
                                                                if (found) {
                                                                    /*
                                                                        aQUI ENTRA A ESTUDIANTE

                                                                     */
                                                                    navController.navigate(NavRoutes.HomeScreenS.ROUTE)
                                                                } else {

                                                                    companyViewModel.setTokenC(token)
                                                                    companyViewModel.fetchCompanyByUserC(
                                                                        userId
                                                                    )
                                                                    companyViewModel.fetchUserCompanyById()
                                                                    companyViewModel.fetchCompanyByUserC(userId)

                                                                    /*

                                                                    AQUI ENTRA A EMPRESA

                                                                     */

                                                                    navController.navigate(NavRoutes.CompanyInfoScreenForCompany.ROUTE)

                                                                }
                                                            }
                                                        } else {
                                                            showAlert = true
                                                        }
                                                    }
                                                } else {
                                                    Log.d(
                                                        "LoginDebug",
                                                        "Credenciales inválidas para $email"
                                                    )
                                                    showAlert = true
                                                }
                                            }
                                        } else {
                                            Log.d("LoginDebug", "Campos vacíos")
                                            showAlert = true
                                        }
                                    }


                                },
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 4f
                                )
                            )
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.forgotPassword),
                                fontSize = 17.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = stringResource(id = R.string.craeteAccount),
                                fontSize = 17.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 25.dp)
                                    .clickable {
                                        navController.navigate(NavRoutes.RegisterOption.ROUTE)
                                    },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }


                if (showAlert) {
                    AlertDialog(
                        onDismissRequest = { showAlert = false },
                        confirmButton = {
                            TextButton(onClick = { showAlert = false }) {
                                Text(stringResource(R.string.acceptText))
                            }
                        },
                        title = { Text(text = stringResource(id = R.string.acceptText)) },
                        text = { Text(text = stringResource(id = R.string.logPopUpText)) }
                    )
                }

            }
        }

    }


}