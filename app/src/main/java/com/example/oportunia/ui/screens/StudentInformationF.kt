package com.example.oportunia.ui.screens



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*

import androidx.compose.material.icons.filled.ArrowDropUp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oportunia.ui.theme.lilRedMain
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import com.example.oportunia.ui.components.texAndLable
import com.example.oportunia.ui.theme.blackPanter
import com.example.oportunia.ui.theme.lilBlue
import com.example.oportunia.ui.theme.lilGray
import com.example.oportunia.ui.theme.walterWhite


@Preview
@Composable
fun RegisterOptionScreenF() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(lilGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
               // .verticalScroll(rememberScrollState())
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
                        color = lilRedMain,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "OportunIA",
                    fontSize = 64.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            // Título
            Text(
                text = "Información",
                fontSize = 32.sp,
                color = blackPanter,
                modifier = Modifier.padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campos de texto
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                var nombre by remember { mutableStateOf("") }
                texAndLable(
                    titulo = "Nombre",
                    placeholder = "ejemplo@correo.com",
                    valor = nombre,
                    alCambiarValor = { nombre = it }
                )

                var apellido1 by remember { mutableStateOf("") }
                texAndLable(
                    titulo = "Primer Apellido",
                    placeholder = "apellido 1",
                    valor = apellido1,
                    alCambiarValor = { apellido1 = it }
                )

                var apellido2 by remember { mutableStateOf("") }
                texAndLable(
                    titulo = "Segundo Apellido",
                    placeholder = "apellido 2",
                    valor = apellido2,
                    alCambiarValor = { apellido2 = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Dropdown de universidad
            var selectedUniversity by remember { mutableStateOf("UNA") }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                UniversityDropdown(
                    selectedUniversity = selectedUniversity,
                    onUniversitySelected = { selectedUniversity = it }
                )
            }

            Spacer(modifier = Modifier.height(150.dp))

            // Botón siguiente
            Box(
                modifier = Modifier
                    .width(350.dp)
                    .padding(horizontal = 60.dp, vertical = 32.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(24.dp),
                        clip = false
                    )
                    .background(
                        color = lilRedMain,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Siguiente",
                    fontSize = 25.sp,
                    color = walterWhite,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}



@Composable
fun UniversityDropdown(
    selectedUniversity: String,
    onUniversitySelected: (String) -> Unit
) {
    val options = listOf("UNA", "UCR", "TEC")
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.width(150.dp)) {
        OutlinedTextField(
            value = selectedUniversity,
            onValueChange = {},
            readOnly = true,
            label = { Text("Universidad", color = Color.Black) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                disabledBorderColor = Color.Black,
                focusedTrailingIconColor = Color.Black,
                unfocusedTrailingIconColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                disabledLabelColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { university ->
                DropdownMenuItem(
                    text = { Text(university, color = Color.Black) },
                    onClick = {
                        onUniversitySelected(university)
                        expanded = false
                    }
                )
            }
        }
    }
}
