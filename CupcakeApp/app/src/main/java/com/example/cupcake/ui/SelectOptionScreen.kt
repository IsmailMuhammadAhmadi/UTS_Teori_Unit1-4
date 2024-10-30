package com.example.cupcake.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.example.cupcake.R
import com.example.cupcake.ui.components.LabelHarga
import com.example.cupcake.ui.theme.CupcakeTheme

/**
 * Tampilan yang menunjukkan daftar opsi [RadioButton].
 */
@Composable
fun PilihanLayar(
    subtotal: String,
    opsi: List<String>,
    ketikaPilihanDiubah: (String) -> Unit = {},
    ketikaDibatalkan: () -> Unit = {},
    ketikaLanjut: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var nilaiTerpilih by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .background(Color(0xFF6A0DAD)) // Latar belakang ungu
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            opsi.forEach { item ->
                Row(
                    modifier = Modifier.selectable(
                        selected = nilaiTerpilih == item,
                        onClick = {
                            nilaiTerpilih = item
                            ketikaPilihanDiubah(item)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = nilaiTerpilih == item,
                        onClick = {
                            nilaiTerpilih = item
                            ketikaPilihanDiubah(item)
                        }
                    )
                    Text(item, color = Color.White) // Teks putih
                }
            }
            Divider(
                color = Color.White,
                thickness = dimensionResource(R.dimen.thickness_divider),
                modifier = Modifier.padding(8.dp)
            )
            LabelHarga(
                subtotal = subtotal,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = ketikaDibatalkan
            ) {
                Text("Batal", color = Color.White)
            }
            Button(
                modifier = Modifier.weight(1f),
                enabled = nilaiTerpilih.isNotEmpty(),
                onClick = ketikaLanjut
            ) {
                Text("Lanjut", color = Color.White)
            }
        }
    }
}
