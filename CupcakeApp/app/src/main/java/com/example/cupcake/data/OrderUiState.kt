package com.example.cupcake.data

/**
 * Kelas data yang merepresentasikan status UI saat ini dalam hal [kuantitas], [rasa],
 * [tanggalPilihan], tanggal pengambilan yang dipilih [tanggal], dan [harga]
 */
data class OrderUiState(
    /** Kuantitas cupcake yang dipilih (1, 6, 12) */
    val kuantitas: Int = 0,
    /** Rasa cupcake dalam pesanan (seperti "Coklat", "Vanilla", dll.) */
    val rasa: String = "",
    /** Tanggal pengambilan yang dipilih (seperti "Jan 1") */
    val tanggal: String = "",
    /** Total harga untuk pesanan */
    val harga: String = "",
    /** Tanggal pengambilan yang tersedia untuk pesanan */
    val opsiPengambilan: List<String> = listOf()
)
