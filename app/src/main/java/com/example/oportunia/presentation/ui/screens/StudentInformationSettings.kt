package com.example.oportunia.presentation.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.ui.theme.lilGray
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import com.example.oportunia.presentation.ui.viewmodel.CompanyViewModel
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel
import com.example.oportunia.presentation.ui.viewmodel.UsersViewModel



@Composable
fun StudentInformationSettings(
    navController: NavHostController,
    usersViewModel: UsersViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel
) {

    val token by usersViewModel.token.collectAsState(initial = null)
    val userId by usersViewModel.userId.collectAsState(initial = null)
    val studentIdd by studentViewModel.studentIdd.collectAsState(initial = null)


    var firstName by remember { mutableStateOf("") }
    var lastName1 by remember { mutableStateOf("") }
    var lastName2 by remember { mutableStateOf("") }


    var expanded by remember { mutableStateOf(false) }
    var selectedUniversityName by remember { mutableStateOf("") }
    var idSelectedU by remember { mutableStateOf(0) }


    val universityList by usersViewModel.universities.collectAsState(initial = emptyList())


    var showAlert by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        usersViewModel.fetchUniversities()
    }

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
            // ——— CABECERA ———
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(Color(0xFF42A5F5), Color(0xFF1976D2))
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 64.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text(stringResource(R.string.nameTitle)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = lastName1,
                    onValueChange = { lastName1 = it },
                    label = { Text(stringResource(R.string.FirstSurnameTitle)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = lastName2,
                    onValueChange = { lastName2 = it },
                    label = { Text(stringResource(R.string.SecondSurnameTitle)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    OutlinedTextField(
                        value = selectedUniversityName,
                        onValueChange = { /* readOnly */ },
                        readOnly = true,
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
                        label = { Text(stringResource(R.string.UniversityTitle), color = Color.Black) },
                        trailingIcon = {
                            if (expanded) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropUp,
                                    contentDescription = "Cerrar menú",
                                    modifier = Modifier.clickable { expanded = false }
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "Abrir menú",
                                    modifier = Modifier.clickable {
                                        expanded = true
                                        usersViewModel.fetchUniversities()
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = !expanded }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        universityList.forEach { uni ->
                            DropdownMenuItem(
                                text = { Text(uni.universityName, fontSize = 16.sp) },
                                onClick = {
                                    selectedUniversityName = uni.universityName
                                    idSelectedU = uni.idUniversity
                                    expanded = false
                                    Log.d(
                                        "StudentInfoSettings",
                                        "Universidad seleccionada: ${uni.universityName} (id=${uni.idUniversity})"
                                    )
                                }
                            )
                        }
                    }
                }


                Spacer(modifier = Modifier.weight(1f))


                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .shadow(8.dp, RoundedCornerShape(24.dp))
                            .background(Color.Red, RoundedCornerShape(24.dp))
                            .clickable {
                                navController.popBackStack()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.Cancelar),
                            fontSize = 18.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }


                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .shadow(10.dp, RoundedCornerShape(24.dp))
                            .background(Color(0xFFAAF0D1), RoundedCornerShape(24.dp))
                            .clickable {

                                Log.d(
                                    "StudentInfoSettings",
                                    "Guardar pulsado con -> firstName: $firstName, lastName1: $lastName1, lastName2: $lastName2, universityId: $idSelectedU, userId: $userId, studentId: $studentIdd, token: $token"
                                )


                                if (
                                    firstName.isNotBlank() &&
                                    lastName1.isNotBlank() &&
                                    lastName2.isNotBlank() &&
                                    selectedUniversityName.isNotBlank() &&
                                    idSelectedU != 0 &&
                                    !token.isNullOrEmpty() &&
                                    userId != null &&
                                    studentIdd != null
                                ) {
                                    // Llamar a updateStudent en StudentViewModel
                                    studentViewModel.updateStudent(
                                        token = token!!,
                                        studentId = studentIdd!!,
                                        rawFirstName = firstName,
                                        rawLastName1 = lastName1,
                                        rawLastName2 = lastName2,
                                        universityId = idSelectedU,
                                        userId = userId!!
                                    )
                                    // Luego, por ejemplo, navegar atrás
                                    navController.popBackStack()
                                } else {
                                    showAlert = true
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.Guardar),
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }


    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            confirmButton = {
                TextButton(onClick = { showAlert = false }) {
                    Text(stringResource(R.string.acceptText))
                }
            },
            title = { Text(stringResource(R.string.studentInfoAlertTittle)) },
            text = { Text(stringResource(R.string.studentInfoAlertText)) }
        )
    }


}