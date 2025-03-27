package com.example.oportunia.presentation.ui.components

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oportunia.presentation.ui.theme.blackPanter
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite



@Composable
fun Header(
    tituloHeader: String,
    colorHeader: Color,
    contenido: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.oportunia.presentation.ui.theme.lilGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(com.example.oportunia.presentation.ui.theme.lilGray)
        ) {

            // Encabezado con color personalizable
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
                        color = colorHeader,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tituloHeader,
                    fontSize = 64.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            // Cuerpo
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(com.example.oportunia.presentation.ui.theme.lilGray)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .background(color = com.example.oportunia.presentation.ui.theme.lilGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Información",
                        fontSize = 32.sp,
                        color = com.example.oportunia.presentation.ui.theme.blackPanter,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .background(color = com.example.oportunia.presentation.ui.theme.lilGray),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        contenido()
                    }
                }
            }
        }
    }
}
