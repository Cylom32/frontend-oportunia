package com.example.oportunia.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oportunia.presentation.ui.theme.OportunIATheme


@Composable
fun NotificationsScreen (modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Welcome Header
        Text(
            text = "Welcome to Notifications",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Your internship journey starts here",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 32.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Action Button
        Button(
            onClick = { /* Handle get started action */ },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Get Started",
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    OportunIATheme {
        NotificationsScreen()
    }
}