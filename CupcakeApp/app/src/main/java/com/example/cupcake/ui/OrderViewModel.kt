package com.example.cupcake.ui

import androidx.lifecycle.ViewModel
import com.example.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Harga per cupcake */
private const val HARGA_PER_CUPCAKE = 49.00

/** Biaya tambahan untuk pengambilan pesanan di hari yang sama */
private const val HARGA_PENGAMBILAN_HARI_INI = 5.00

/**
 * [OrderViewModel] menyimpan informasi pesanan cupcake dalam hal jumlah, rasa, dan
 * tanggal pengambilan. Model ini juga tahu cara menghitung total harga.
 */
class OrderViewModel : ViewModel() {

    // Mengelola status UI untuk pesanan ini
    private val _uiState = MutableStateFlow(OrderUiState(opsiPengambilan = opsiPengambilan()))
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    // Menentukan jumlah cupcake dalam pesanan
    fun setJumlah(cupcake: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                kuantitas = cupcake,
                harga = hitungHarga(jumlah = cupcake)
            )
        }
    }

    // Menentukan rasa cupcake dalam pesanan
    fun setRasa(rasaDiinginkan: String) {
        _uiState.update { currentState ->
            currentState.copy(rasa = rasaDiinginkan)
        }
    }

    // Menentukan tanggal pengambilan dan memperbarui harga
    fun setTanggal(tanggalPengambilan: String) {
        _uiState.update { currentState ->
            currentState.copy(
                tanggal = tanggalPengambilan,
                harga = hitungHarga(tanggalPengambilan = tanggalPengambilan)
            )
        }
    }

    // Reset status pesanan
    fun resetPesanan() {
        _uiState.value = OrderUiState(opsiPengambilan = opsiPengambilan())
    }

    // Menghitung harga berdasarkan detail pesanan
    private fun hitungHarga(
        jumlah: Int = _uiState.value.kuantitas,
        tanggalPengambilan: String = _uiState.value.tanggal
    ): String {
        var hargaHitung = jumlah * HARGA_PER_CUPCAKE
        if (opsiPengambilan()[0] == tanggalPengambilan) {
            hargaHitung += HARGA_PENGAMBILAN_HARI_INI
        }
        return NumberFormat.getCurrencyInstance().format(hargaHitung)
    }

    // Menghasilkan opsi tanggal pengambilan
    private fun opsiPengambilan(): List<String> {
        val opsiTanggal = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val kalender = Calendar.getInstance()
        repeat(4) {
            opsiTanggal.add(formatter.format(kalender.time))
            kalender.add(Calendar.DATE, 1)
        }
        return opsiTanggal
    }
}
