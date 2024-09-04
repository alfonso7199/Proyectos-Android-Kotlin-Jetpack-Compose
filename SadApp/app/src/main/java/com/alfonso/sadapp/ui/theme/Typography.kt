package com.alfonso.sadapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alfonso.sadapp.R

// Define a custom font family
val CustomFontFamily = FontFamily(
    Font(R.font.robotoregular, FontWeight.Normal),
    Font(R.font.robotobold, FontWeight.Bold)
)

val AppTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
    // Define other text styles as needed
)