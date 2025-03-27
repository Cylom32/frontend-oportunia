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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.oportunia.ui.theme.lilRedMain
import androidx.navigation.NavHostController
import com.example.oportunia.ui.screens.PasswordLabel
import com.example.oportunia.ui.viewmodel.UsersViewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.ui.viewmodel.StudentViewModel


@Composable
fun LogScreen(navController: NavHostController, usersViewModel: UsersViewModel, studentViewModel: StudentViewModel) {


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lilRedMain),

            ) {
            // Primer Box arribaaaa
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.shakehands1062821),
                        contentDescription = "Descripción accesible",
                        modifier = Modifier.size(150.dp).padding(top = 20.dp,bottom = 0.dp),
                        colorFilter = ColorFilter.tint(Color.White),/// para cambiar el color de la imagen
                        // o es mejor simplemente subir la imagen blanca?


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

            // Segundo Box abajooo
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(color = lilRedMain)
                    .padding(start = 60.dp, top = 120.dp, end = 60.dp, bottom = 0.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


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
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(10.dp),
                                clip = true
                            )
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(10.dp)
                            ) // Fondo fuera del TextField
                    ) {

                        com.example.oportunia.ui.screens.Label(
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
                            .shadow(
                                elevation = 6.dp,
                                shape = RoundedCornerShape(10.dp),
                                clip = true
                            )
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(10.dp)
                            ) // Fondo fuera del TextField
                    ) {
                        // Label(email = password, onEmailChange = { password = it }, "Escriba su contraseña")
                        PasswordLabel(value = password, onValueChange = { password = it }, "")

                    }

                }

            }

            Box(   /// tercer box
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(lilRedMain)
                    .padding(start = 80.dp, top = 0.dp, end = 80.dp, bottom = 0.dp),
                contentAlignment = Alignment.TopCenter
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(), // ocupa toda la altura
                    verticalArrangement = Arrangement.SpaceBetween, // separa arriba y abajo
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Parte de arriba
                    val coroutineScope = rememberCoroutineScope()

                    Text(
                        text = "Login",
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .clickable {
                                coroutineScope.launch {
                                    usersViewModel.validateUserCredentials(email, password) { isValid ->
                                        if (isValid) {
                                            navController.navigate("home")
                                            val userId = usersViewModel.getAuthenticatedUserId()
                                            Log.d("LoginDebug", "Usuario autenticado con ID: $userId")
                                            usersViewModel.selectUserById(userId!!)

                                            studentViewModel.loadStudentByUserId(usersViewModel.selectedUserIdValue())
                                            //arriba le estoy pasado el id de user al modelo de student


                                        } else {
                                            Log.d("LoginDebug", "Credenciales inválidas para $email")

                                        }
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


                    // Parte de abajo
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.forgotPassword),
                            fontSize = 17.sp,
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
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
        }
    }
}