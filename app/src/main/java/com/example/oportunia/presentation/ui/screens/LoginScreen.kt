package com.example.oportunia.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.oportunia.presentation.ui.theme.OportunIATheme
import com.example.oportunia.presentation.ui.theme.lilRedMain



import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.navigation.NavHostController


@Composable
fun LoginScreen(navController: NavHostController, paddingValues: PaddingValues)

 {
     Surface(
         modifier = Modifier
             .fillMaxSize()
             .padding(0.dp)
     ) {
         Column(
             modifier = Modifier
                 .fillMaxSize()
                 .background(com.example.oportunia.presentation.ui.theme.lilRedMain),

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
                         text = "OportunIA",
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
                     .background(color = com.example.oportunia.presentation.ui.theme.lilRedMain)
                     .padding(start = 60.dp, top = 50.dp, end = 60.dp, bottom = 100.dp),
                 contentAlignment = Alignment.TopCenter
             ) {
                 Column(
                     horizontalAlignment = Alignment.CenterHorizontally
                 ) {
                     var email by remember { mutableStateOf("") }
                     var password by remember { mutableStateOf("") }

                     Text(
                         text = "Correo electrónico",
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

                         Label(email = email, onEmailChange = { email = it }, "Escriba su correo")




                     }

                     Text(
                         text = "Contraseña",
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
                         PasswordLabel(value = password, onValueChange = { password = it }, "Contraseña")

                     }
                     // Botón de Iniciar sesión reemplazando el Text anterior
                     Text(
                         text = "Iniciar sesión",
                         fontSize = 24.sp,
                         color = Color.White,
                         modifier = Modifier
                             .fillMaxWidth()
                             .clickable {
                                 navController.navigate("cv") // Aquí se navega a CVScreen
                             }
                             .padding(top = 16.dp),
                         textAlign = TextAlign.Center,
                         style = TextStyle(
                             shadow = Shadow(
                                 color = Color.Black,
                                 offset = Offset(2f, 2f),
                                 blurRadius = 4f
                             )
                         )
                     )



                 }

             }

             Box(   /// tercer box
                 modifier = Modifier
                     .height(150.dp)
                     .fillMaxWidth()
                     .background(com.example.oportunia.presentation.ui.theme.lilRedMain)
                     .padding(start = 80.dp, top = 0.dp, end = 80.dp, bottom = 0.dp),
                 contentAlignment = Alignment.TopCenter
             ) {

                 Column(
                     horizontalAlignment = Alignment.CenterHorizontally
                 ) {
                     Text(
                         text = "¿Olvidó la contraseña?",
                         fontSize = 17.sp,
                         color = Color.White,

                         modifier = Modifier
                             .fillMaxWidth(),
                         textAlign = TextAlign.Center
                     )
                     Text(
                         text = "Crear cuenta",
                         fontSize = 17.sp,
                         color = Color.White,
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(top = 15.dp),
                         textAlign = TextAlign.Center
                     )

                 }
             }
         }
     }
}




@Composable
fun Label(email: String, onEmailChange: (String) -> Unit, placeholder: String) {
    Box(
        modifier = Modifier
            .width(1000.dp)
            .height(50.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                clip = true
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        BasicTextField(
            value = email,
            onValueChange = onEmailChange,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            modifier = Modifier.fillMaxSize(),
            decorationBox = { innerTextField ->
                if (email.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )
    }
}


@Composable
fun PasswordLabel(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(1000.dp)
            .height(50.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                clip = true
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 12.dp, vertical = 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Black
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = 12.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                }
            )
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                val icon = if (passwordVisible)
                    painterResource(id = R.drawable.eyepassword)
                else
                    painterResource(id = R.drawable.eyepassword)

                Icon(
                    painter = icon,
                    contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }


        }
    }
}

@Composable
fun LoginScreenPreviewContent() {
    // Copia del contenido sin necesidad de navController
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        @Composable
        fun LoginScreen(navController: NavHostController)
        {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(com.example.oportunia.presentation.ui.theme.lilRedMain),

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
                                text = "OportunIA",
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
                            .background(color = com.example.oportunia.presentation.ui.theme.lilRedMain)
                            .padding(start = 60.dp, top = 50.dp, end = 60.dp, bottom = 20.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var email by remember { mutableStateOf("") }
                            var password by remember { mutableStateOf("") }

                            Text(
                                text = "Correo electrónico",
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

                                Label(email = email, onEmailChange = { email = it }, "Escriba su correo")




                            }

                            Text(
                                text = "Contraseña",
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
                                    ),

                            ) {
                                // Label(email = password, onEmailChange = { password = it }, "Escriba su contraseña")
                                PasswordLabel(value = password, onValueChange = { password = it }, "Contraseña")
                                Text(
                                    text = "Iniciar sesión",
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.navigate("cv") // Aquí se navega a CVScreen
                                        }
                                        .padding(top = 16.dp),
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        shadow = Shadow(
                                            color = Color.Black,
                                            offset = Offset(2f, 2f),
                                            blurRadius = 4f
                                        )
                                    )
                                )
                            }





                        }

                    }

                    Box(   /// tercer box
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .background(com.example.oportunia.presentation.ui.theme.lilRedMain)
                            .padding(start = 80.dp, top = 0.dp, end = 80.dp, bottom = 0.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {


                            Text(
                                text = "¿Olvidó la contrasASDAeña?",
                                fontSize = 17.sp,
                                color = Color.White,

                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Crear cuenta",
                                fontSize = 17.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 15.dp),
                                textAlign = TextAlign.Center
                            )

                        }
                    }
                }
            }
        }




        @Composable
        fun Label(email: String, onEmailChange: (String) -> Unit, placeholder: String) {
            Box(
                modifier = Modifier
                    .width(1000.dp)
                    .height(50.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(10.dp),
                        clip = true
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 12.dp)
            ) {
                BasicTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.fillMaxSize(),
                    decorationBox = { innerTextField ->
                        if (email.isEmpty()) {
                            Text(
                                text = placeholder,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                )
            }
        }


        @Composable
        fun PasswordLabel(
            value: String,
            onValueChange: (String) -> Unit,
            placeholder: String
        ) {
            var passwordVisible by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .width(1000.dp)
                    .height(50.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(10.dp),
                        clip = true
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(vertical = 12.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        decorationBox = { innerTextField ->
                            Box {
                                if (value.isEmpty()) {
                                    Text(
                                        text = placeholder,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        val icon = if (passwordVisible)
                            painterResource(id = R.drawable.eyepassword)
                        else
                            painterResource(id = R.drawable.eyepassword)

                        Icon(
                            painter = icon,
                            contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }


                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreviewSimple() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(com.example.oportunia.presentation.ui.theme.lilRedMain),
        ) {
            // Primer Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.shakehands1062821),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(150.dp)
                            .padding(top = 20.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    Text(
                        text = "OportunIA",
                        fontSize = 64.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Segundo Box
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp, vertical = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }

                    Text("Correo electrónico", color = Color.White, fontSize = 17.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Label(email = email, onEmailChange = { email = it }, "Escriba su correo")

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Contraseña", color = Color.White, fontSize = 17.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    PasswordLabel(value = password, onValueChange = { password = it }, "Contraseña")

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Iniciar sesión",
                        fontSize = 24.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            shadow = Shadow(Color.Black, Offset(2f, 2f), 4f)
                        )
                    )
                }
            }

            // Tercer Box
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .background(com.example.oportunia.presentation.ui.theme.lilRedMain),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("¿Olvidó la contraseña?", color = Color.White, fontSize = 17.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Crear cuenta", color = Color.White, fontSize = 17.sp)
                }
            }
        }
    }
}

