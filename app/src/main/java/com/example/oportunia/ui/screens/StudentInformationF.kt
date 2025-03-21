package com.example.oportunia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oportunia.ui.theme.OportunIATheme
import com.example.oportunia.ui.theme.lilRedMain





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
import androidx.compose.ui.unit.sp
import com.example.oportunia.ui.theme.OportunIATheme
import com.example.oportunia.ui.theme.lilRedMain



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
import com.example.oportunia.ui.theme.blackPanter
import com.example.oportunia.ui.theme.lilGray
import com.example.oportunia.ui.theme.walterWhite

@Preview
@Composable
fun RegisterOptionScreenF() {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(lilGray)
    ) {


        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .background(lilGray)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(lilGray),

                ) {

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
                            color = lilRedMain,
                            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        ),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(
                        text = "OportunIA",
                        fontSize = 64.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize().background(lilGray),

                    ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth().padding(top = 20.dp)
                            .background(color = lilGray),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = "Información",
                            fontSize = 32.sp,
                            color = blackPanter,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 24.dp)
                        )

                    }




                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .background(color = lilGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        ) {
                            Text(
                                text = "Nombre",
                                fontSize = 18.sp,
                                color = blackPanter,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            var nombre by remember { mutableStateOf("") }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .shadow(4.dp, shape = RoundedCornerShape(12.dp))
                                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 12.dp)
                            ) {
                                BasicTextField(
                                    value = nombre,
                                    onValueChange = { nombre = it },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    decorationBox = { innerTextField ->
                                        if (nombre.isEmpty()) {
                                            Text(
                                                text = "Escriba su nombre",
                                                color = Color.Gray
                                            )
                                        }
                                        innerTextField()
                                    }
                                )
                            }
                        }
                    }









                }



            }


        }







    }


}




@Composable
fun texAndLable(
    titulo: String,
    placeholder: String,
    valor: String,
    alCambiarValor: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = titulo,
            fontSize = 18.sp,
            color = blackPanter,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .shadow(4.dp, shape = RoundedCornerShape(12.dp))
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            BasicTextField(
                value = valor,
                onValueChange = alCambiarValor,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box {
                        if (valor.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}
