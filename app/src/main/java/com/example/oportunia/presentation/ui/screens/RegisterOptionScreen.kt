package com.example.oportunia.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oportunia.presentation.ui.theme.lilRedMain
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import com.example.oportunia.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.oportunia.presentation.navigation.NavRoutes
import com.example.oportunia.presentation.ui.theme.blackPanter
import com.example.oportunia.presentation.ui.theme.lilGray
import com.example.oportunia.presentation.ui.theme.walterWhite
import androidx.compose.ui.res.stringResource

@Composable
fun RegisterOptionScreen(navController: NavHostController) {


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(com.example.oportunia.presentation.ui.theme.lilGray)
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(com.example.oportunia.presentation.ui.theme.lilGray),

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
                            color = com.example.oportunia.presentation.ui.theme.lilRedMain,
                            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        ),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontSize = 64.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .background(color = com.example.oportunia.presentation.ui.theme.lilGray),
                    contentAlignment = Alignment.Center
                )


                {
                    Text(
                        text = stringResource(R.string.userTypTitle),
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
                        .padding(30.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(24.dp),
                            clip = false
                        )
                        .background(
                            color = com.example.oportunia.presentation.ui.theme.walterWhite,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable {
                            navController.navigate(NavRoutes.RegisterInformationF.ROUTE)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.userTypeEst),
                        fontSize = 32.sp,
                        color = com.example.oportunia.presentation.ui.theme.blackPanter,
                        textAlign = TextAlign.Center
                    )
                }


                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(30.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(24.dp),
                            clip = false
                        )
                        .background(
                            color = com.example.oportunia.presentation.ui.theme.walterWhite,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.userTypeCom),
                        fontSize = 32.sp,
                        color = com.example.oportunia.presentation.ui.theme.blackPanter,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(0.dp)

                ) {

                }


            }

        }


    }


}


