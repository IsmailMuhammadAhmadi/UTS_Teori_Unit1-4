package com.example.cupcake.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cupcake.R
import com.example.cupcake.data.DataSource

/**
 * Tampilan yang memungkinkan pengguna memilih jumlah cupcake yang diinginkan.
 */
@Composable
fun MulaiPesananLayar(
    opsiJumlah: List<Pair<Int, Int>>,
    ketikaLanjut: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color(0xFF6A0DAD)) // Latar belakang ungu
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.cupcake),
                contentDescription = null,
                modifier = Modifier.width(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pesan Cupcake",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            opsiJumlah.forEach { item ->
                TombolPilihJumlah(
                    idLabel = item.first,
                    ketikaKlik = { ketikaLanjut(item.second) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun TombolPilihJumlah(
    idLabel: Int,
    ketikaKlik: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = ketikaKlik,
        modifier = modifier.widthIn(min = 200.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(stringResource(idLabel), color = Color(0xFF6A0DAD))
    }
}
