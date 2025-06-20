package com.example.oportunia.presentation.ui.screens

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oportunia.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import com.example.oportunia.presentation.ui.theme.lilGray
import androidx.compose.material3.Surface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavHostController
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.theme.deepSkyBlue
import com.example.oportunia.presentation.ui.theme.midnightBlue
import com.example.oportunia.presentation.ui.theme.royalBlue
import com.example.oportunia.presentation.ui.viewmodel.StudentState
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel


@Composable
fun CVScreen(
    studentViewModel: StudentViewModel,
    navController: NavHostController
) {


    val studentState by studentViewModel.studentState.collectAsState()

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
                        brush = Brush.linearGradient(
                            colors = listOf(
                                royalBlue,
                                deepSkyBlue,
                                midnightBlue
                            ),
                            start = Offset(0f, 0f),
                            end = Offset(1000f, 1000f)
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            )
            {
                when (studentState) {
                    is StudentState.Loading -> {
                        Text(
                            text = stringResource(R.string.cargando),
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    is StudentState.Success -> {
                        val student = (studentState as StudentState.Success).student

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${student.name} ${student.lastName1}",
                                color = Color.White,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                        }
                    }


                    is StudentState.Error -> {
                        val message = (studentState as StudentState.Error).message
                        Text(
                            text = "Error: $message",
                            color = Color.Red,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    StudentState.Empty -> {
                        Text(
                            text = stringResource(R.string.sin_datos),
                            color = Color.LightGray,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Título
            Text(
                text = stringResource(R.string.titleCV),
                fontSize = 32.sp,
                color = com.example.oportunia.presentation.ui.theme.blackPanter,
                modifier = Modifier.padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            // Buttons Section
            ButtonSection(navController)
        }
    }

}


@Composable
fun ButtonSection(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                navController.navigate(NavRoutes.EditUCVScreen.ROUTE)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = com.example.oportunia.presentation.ui.theme.walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(24.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        )

        {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit CV",
                modifier = Modifier.size(32.dp),
                tint = Color.Black
            )

            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.boton_editar_cv),
                fontSize = 22.sp,
                color = Color.Black
            )
        }


        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {
                navController.navigate(NavRoutes.CvAnalisys.ROUTE)


            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = com.example.oportunia.presentation.ui.theme.walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(34.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ia_brain),
                contentDescription = "Neuro CV",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.neuro_cv),
                fontSize = 22.sp,
                color = Color.Black
            )
        }
    }
}

