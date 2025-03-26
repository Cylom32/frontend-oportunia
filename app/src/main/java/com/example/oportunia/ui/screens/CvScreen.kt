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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oportunia.R
import com.example.oportunia.ui.theme.OportunIATheme
import com.example.oportunia.ui.theme.lilBlue
import com.example.oportunia.ui.viewmodel.StudentState
import com.example.oportunia.ui.viewmodel.StudentViewModel
import androidx.compose.runtime.getValue
import com.example.oportunia.ui.theme.walterWhite


@Composable
fun CVScreen(
    modifier: Modifier = Modifier,
    studentViewModel: StudentViewModel
)
 {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(alpha = 0.2f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        HeaderSection(studentViewModel)
        // Spacer to push buttons down
        Spacer(modifier = Modifier.height(100.dp))
        // Buttons Section
        ButtonSection()

        Spacer(modifier = Modifier.weight(1f)) // Push buttons up and leave space for bottom nav
    }
}




@Composable
fun HeaderSection(studentViewModel: StudentViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(lilBlue)
            .padding(16.dp),
            contentAlignment = Alignment.Center
    ) {
        val studentState by studentViewModel.studentState.collectAsState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (studentState) {
                is StudentState.Loading -> {
                    Text(
                        text = "Cargando...",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                is StudentState.Success -> {
                    val student = (studentState as StudentState.Success).student
                    Text(
                        text = "${student.name} ${student.lastName1}",
                        color = Color.White,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Curriculum Vitae",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Medium
                    )
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
                        text = "Sin datos de estudiante",
                        color = Color.LightGray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

}
}




@Composable
fun ButtonSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Button(
            onClick = { /* Handle Edit CV action */ },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(90.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit CV",
                modifier = Modifier.size(32.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Editar CV",
                fontSize = 22.sp,
                color = Color.Black
            )
        }

        Button(
            onClick = { /* Handle Neuro CV action */ },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(90.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = walterWhite,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ia_brain),
                contentDescription = "Neuro CV",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Neuro CV",
                fontSize = 22.sp,
                color = Color.Black
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun CVScreenPreview() {
//    OportunIATheme {
//        CVScreen()
//    }
//}
