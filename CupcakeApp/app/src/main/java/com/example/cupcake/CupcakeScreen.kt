package com.example.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.filled.ArrowBack
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.*

enum class CupcakeScreen(@StringRes val title: Int) {
    Mulai(title = R.string.app_name),
    Rasa(title = R.string.choose_flavor),
    Pengambilan(title = R.string.choose_pickup_date),
    Ringkasan(title = R.string.order_summary)
}

@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Mulai.name
    )

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Mulai.name,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            composable(route = CupcakeScreen.Mulai.name) {
                MulaiPesananLayar(
                    opsiJumlah = DataSource.kuantitasOpsi,
                    ketikaLanjut = {
                        viewModel.setJumlah(it)
                        navController.navigate(CupcakeScreen.Rasa.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }
            composable(route = CupcakeScreen.Rasa.name) {
                val context = LocalContext.current
                PilihanLayar(
                    subtotal = uiState.harga,
                    ketikaLanjut = { navController.navigate(CupcakeScreen.Pengambilan.name) },
                    ketikaDibatalkan = {
                        batalkanPesananDanNavigasiMulai(viewModel, navController)
                    },
                    opsi = DataSource.rasa.map { id -> context.resources.getString(id) },
                    ketikaPilihanDiubah = { viewModel.setRasa(it) },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = CupcakeScreen.Pengambilan.name) {
                PilihanLayar(
                    subtotal = uiState.harga,
                    ketikaLanjut = { navController.navigate(CupcakeScreen.Ringkasan.name) },
                    ketikaDibatalkan = {
                        batalkanPesananDanNavigasiMulai(viewModel, navController)
                    },
                    opsi = uiState.opsiPengambilan,
                    ketikaPilihanDiubah = { viewModel.setTanggal(it) },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = CupcakeScreen.Ringkasan.name) {
                val context = LocalContext.current
                RingkasanPesananLayar(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        batalkanPesananDanNavigasiMulai(viewModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String ->
                        kirimPesanan(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

private fun batalkanPesananDanNavigasiMulai(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetPesanan()
    navController.popBackStack(CupcakeScreen.Mulai.name, inclusive = false)
}

private fun kirimPesanan(context: Context, subject: String, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}
