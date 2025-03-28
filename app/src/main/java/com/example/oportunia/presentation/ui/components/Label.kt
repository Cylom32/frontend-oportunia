package com.example.oportunia.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Label(email: String, onEmailChange: (String) -> Unit, placeholder: String) {
    Box(
        modifier = Modifier
            .width(1000.dp)
            .height(50.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(10.dp),
                clip = true
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 12.dp, vertical = 12.dp)
    ) {
        BasicTextField(
            value = email,
            onValueChange = onEmailChange,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            modifier = Modifier.fillMaxSize(),
            decorationBox = { innerTextField ->
                if (email.isEmpty()) {
                    Text(
                        text = placeholder,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        )
    }
}

