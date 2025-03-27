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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.ui.theme.OportunIATheme


//@Preview
@Composable
fun SettingScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.2f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        HeaderSectionSettings()
        ButtonSectionSettings()
        //Spacer(modifier = Modifier.weight(1f))
    }

}


@Composable
fun HeaderSectionSettings() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1A2A44))
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { /* Handle Edit Account action */ },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1A2A44) // Dark blue background
            )

        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Edit Account",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Cuenta", fontSize = 18.sp)
        }

        Button(
            onClick = { /* Handle Edit Language action */ },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1A2A44) // Dark blue background
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Edit Account",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Idioma", fontSize = 18.sp)
        }


        Button(
            onClick = { /* Handle Edit Privacity action */ },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1A2A44) // Dark blue background
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Edit Privacity",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Privacidad", fontSize = 18.sp)
        }

        Button(
            onClick = { /* Handle Logout action */ },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1A2A44) // Dark blue background
            )

        ) {
            Image(
                painter= painterResource(id = R.drawable.logout),
                contentDescription = "Logout",
                modifier = Modifier.size(24.dp) ,

                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Logout", fontSize = 18.sp)
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