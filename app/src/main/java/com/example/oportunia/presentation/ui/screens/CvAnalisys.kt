package com.example.oportunia.presentation.ui.screens



import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oportunia.R
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite
import com.example.oportunia.presentation.ui.viewmodel.StudentViewModel

@Composable
fun CvAnalisys(
    studentViewModel: StudentViewModel,
    paddingValues: PaddingValues
) {
    var selectedFileName by remember { mutableStateOf("") }
    val uploadState by studentViewModel.uploadState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                selectedFileName = uri.lastPathSegment ?: "PDF seleccionado"
                studentViewModel.sendCv(uri, "JSJ", -1)
            }
        }
    )

    Scaffold { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(lilGray)
                // Aplica tanto el padding del Scaffold (innerPadding) como el de la barra inferior (paddingValues)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(layoutDirection = androidx.compose.ui.unit.LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(layoutDirection = androidx.compose.ui.unit.LayoutDirection.Ltr),
                    bottom = paddingValues.calculateBottomPadding().coerceAtLeast(innerPadding.calculateBottomPadding())
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Banner superior redondeado
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.linearGradient(
                                colors = gradientColorsBlue,
                                start = Offset(0f, 0f),
                                end = Offset(1000f, 1000f)
                            ),
                            shape = RoundedCornerShape(
                                bottomStart = 32.dp,
                                bottomEnd = 32.dp
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.Analisis_cv),
                        color = walterWhite,
                        fontSize = 39.sp
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { launcher.launch(arrayOf("application/pdf")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = walterWhite
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = stringResource(R.string.Seleccionar_PDF))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (selectedFileName.isNotBlank()) {
//                        Text(
//                            text = "Archivo seleccionado: $selectedFileName",
//                            fontSize = 16.sp,
//                            modifier = Modifier.fillMaxWidth()
//                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    when (uploadState) {
                        is StudentViewModel.CvUploadState.Idle -> { /* Nada */ }
                        is StudentViewModel.CvUploadState.Loading -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = stringResource(R.string.Analizando_PDF), fontSize = 16.sp)
                            }
                        }
                        is StudentViewModel.CvUploadState.Success -> {
                            val response =
                                (uploadState as StudentViewModel.CvUploadState.Success).response
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringResource(R.string.Analisis_completo),
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = response.analysis,
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                )
                            }
                        }
                        is StudentViewModel.CvUploadState.Error -> {
                            val message =
                                (uploadState as StudentViewModel.CvUploadState.Error).message
                            Text(
                                text = "Error: $message",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 16.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}
