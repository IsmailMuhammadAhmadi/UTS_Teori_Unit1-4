package com.example.cupcake.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import com.example.cupcake.R

/**
 * Composable yang menampilkan [harga] terformat untuk ditampilkan di layar.
 */
@Composable
fun LabelHarga(subtotal: String, modifier: Modifier = Modifier, color: Color = Color.White) {
    Text(
        text = stringResource(R.string.subtotal_price, subtotal),
        modifier = modifier
            .padding(16.dp)
            .background(Color(0xFF6A0DAD)), // Latar belakang ungu
        style = MaterialTheme.typography.headlineSmall,
        color = color, // Menggunakan parameter color
        textAlign = TextAlign.End // Teks rata kanan
    )
}
