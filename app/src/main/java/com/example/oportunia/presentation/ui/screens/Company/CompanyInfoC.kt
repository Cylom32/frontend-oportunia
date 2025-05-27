package com.example.oportunia.presentation.ui.screens.Company

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.oportunia.R
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.components.gradientBackgroundBlue
import com.example.oportunia.presentation.ui.screens.BottomNavigationBar
import com.example.oportunia.presentation.ui.theme.gradientColorsBlue
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite


@Composable
fun CompanyInfoScreenC(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = NavRoutes.CompanyInfoScreenS.ROUTE,
                onScreenSelected = { route ->
                    if (route != NavRoutes.CompanyInfoScreenS.ROUTE) {
                        navController.navigate(route)
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding())
                .background(lilGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header degradado
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                            clip = false
                        )
                        .gradientBackgroundBlue(
                            gradientColorsBlue,
                            RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Gabriel Chavarria",
                        color = walterWhite,
                        fontSize = 39.sp
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Card que ocupa todo el espacio restante
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // --- Parte superior ---
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.intellogo),
                                    contentDescription = "Logo empresa",
                                    modifier = Modifier.size(80.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "Intel Corporation",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Investing in Costa Rica",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )

                            Text(
                                text = "Since 1997, Intel’s presence in Costa Rica has supported the growth of the country and catalyzed Foreign Direct Investment. More than 2000 employees design, prototype, test, and validate integrated circuit and software solutions, and provide finance, human resources, procurement, and sales and marketing support.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        // --- Fila de tus 4 íconos al pie ---
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.shakehands1062821),
                                contentDescription = "Red 1",
                                modifier = Modifier.size(32.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.shakehands1062821),
                                contentDescription = "Red 2",
                                modifier = Modifier.size(32.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.shakehands1062821),
                                contentDescription = "Red 3",
                                modifier = Modifier.size(32.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.shakehands1062821),
                                contentDescription = "Otro",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}