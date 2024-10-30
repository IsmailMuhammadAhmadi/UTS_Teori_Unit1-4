package com.example.cupcake.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cupcake.R
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.components.LabelHarga
import com.example.cupcake.ui.theme.CupcakeTheme

/**
 * Composable ini mengharapkan [orderUiState] yang merepresentasikan status pesanan,
 * [onCancelButtonClicked] untuk membatalkan pesanan dan mengirimkan pesanan akhir ke
 * [onSendButtonClicked].
 */
@Composable
fun RingkasanPesananLayar(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources

    val jumlahCupcakes = resources.getQuantityString(
        R.plurals.cupcakes,
        orderUiState.kuantitas,
        orderUiState.kuantitas
    )
    val ringkasanPesanan = stringResource(
        R.string.order_summary,
        jumlahCupcakes,
        orderUiState.rasa,
        orderUiState.tanggal,
        orderUiState.kuantitas
    )
    val pesananBaru = stringResource(R.string.new_cupcake_order)

    val items = listOf(
        Pair(stringResource(R.string.quantity), jumlahCupcakes),
        Pair(stringResource(R.string.flavor), orderUiState.rasa),
        Pair(stringResource(R.string.pickup_date), orderUiState.tanggal)
    )
    
    Column(
        modifier = modifier.background(Color(0xFF6A0DAD)).padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items.forEach { item ->
                Text(item.first.uppercase(), color = Color.White)
                Text(text = item.second, fontWeight = FontWeight.Bold, color = Color.White)
                Divider(thickness = dimensionResource(R.dimen.thickness_divider))
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
            // Mengatur subtotal menjadi merah
            LabelHarga(
                subtotal = orderUiState.harga, // Pastikan Anda menggunakan 'subtotal' bukan 'harga'
                modifier = Modifier.align(Alignment.End),
                color = Color.Red // Set warna merah di sini
            )
        }
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSendButtonClicked(pesananBaru, ringkasanPesanan) }
                ) {
                    Text(stringResource(R.string.send))
                }
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onCancelButtonClicked
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        }
    }
}