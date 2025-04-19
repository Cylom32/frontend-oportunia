package com.example.oportunia.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.ui.theme.*
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import java.io.File
import java.io.FileOutputStream

@Preview()
@Composable
fun RequestScreen() {
    BoxWithConstraints {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Surface(
            modifier = Modifier
                .fillMaxSize(),
              //  .background(lilRed),
            color = lilGray
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = screenWidth * 0.06f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Archivo Adjunto",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.07f)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "CV John Doe1.pdf",
                        fontSize = (screenWidth.value * 0.045).sp
                    )
                }

                Text(
                    text = "Mensaje Adicional",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.25f)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Kkjhaskjdas daksdjaskdasdnmasdhk akjsdahs daksd dk asdaksdjakjsdha sdkasjasad aksdajhsdads akddjahsda djhasdj ccccc:",
                        fontSize = (screenWidth.value * 0.04).sp
                    )
                }

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = lilRed),
                    modifier = Modifier
                        .height(screenHeight * 0.07f)
                        .width(screenWidth * 0.4f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Aplicar",
                        color = Color.White,
                        fontSize = (screenWidth.value * 0.05).sp
                    )
                }
            }
        }
    }
}
