package com.example.oportunia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.ui.theme.OportunIATheme
import com.example.oportunia.ui.theme.blackPanter
import com.example.oportunia.ui.theme.lilBlue
import com.example.oportunia.ui.theme.lilGray
import com.example.oportunia.ui.theme.walterWhite
import com.example.oportunia.ui.viewmodel.StudentState


//@Preview
@Composable
fun SettingScreen(modifier: Modifier = Modifier){

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(lilGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lilGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Encabezado
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                        clip = false
                    )
                    .background(
                        color = lilBlue,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.titleSettings),
                    color = walterWhite,
                    fontSize = 39.sp,
                  //  fontWeight = FontWeight.Bold
                )
            }



            Spacer(modifier = Modifier.height(25.dp))
            // Buttons Section
            ButtonSectionSettings()
        }
    }






//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .background(Color.LightGray.copy(alpha = 0.2f)),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
//        HeaderSectionSettings()
//        Spacer(modifier = Modifier.weight(1f))
//        ButtonSectionSettings()
//        Spacer(modifier = Modifier.weight(1f))
//    }

}


@Composable
fun HeaderSectionSettings() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                clip = false
            )
            .background(lilBlue, shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally // Center the text horizontally
        ) {

            Text(
                text = "Settings",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable

fun ButtonSectionSettings() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Eliminamos .height(90.dp) para que la columna crezca según el contenido
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { /* Handle Edit Account action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp), // Reducimos la altura para que sea consistente con los otros botones
            colors = ButtonDefaults.buttonColors(
                containerColor = walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(34.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Edit Account",
                modifier = Modifier.size(32.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Cuenta", fontSize = 22.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { /* Handle Edit Language action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(34.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Edit Account",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(34.dp))
            Text(text = "Idioma", fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle Logout action */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = walterWhite, // Cambiamos el color de fondo a walterWhite
                contentColor = Color.Black // Cambiamos el color del contenido a negro
            ),
            shape = RoundedCornerShape(34.dp), // Añadimos la misma forma que el botón "Cuenta"
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp) // Añadimos la misma elevación
        ) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Logout",
                modifier = Modifier.size(32.dp),
                colorFilter = ColorFilter.tint(Color.Black) // Cambiamos el tint del ícono a negro para que coincida con el contentColor
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Logout",
                fontSize = 22.sp,
                color = Color.Black // Aseguramos que el texto sea negro
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettinsScreenPreview() {
    OportunIATheme {
        SettingScreen()
    }
}