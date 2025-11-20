package com.example.arsitekturmvvm

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.arsitekturmvvm.view.FormSiswa
import com.example.arsitekturmvvm.model.DataJK.Jenisk
import com.example.arsitekturmvvm.view.TampilSiswa
import com.example.arsitekturmvvm.viewmodel.SiswaViewModel

// List navigasi
enum class Navigasi {
    Formulirku,
    Detail
}

@Composable
fun SiswaApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: SiswaViewModel = viewModel()
) {
    val uiState = viewModel.statusUI.collectAsState()

    Scaffold { isiRuang ->
        NavHost(
            navController = navController,
            startDestination = Navigasi.Formulirku.name,
            modifier = modifier.padding(isiRuang)
        ) {

            // Halaman Form Input
            composable(route = Navigasi.Formulirku.name) {
                val context = LocalContext.current
                FormSiswa(
                    pilihanJk = Jenisk.map { id -> context.getString(id) },
                    onSubmitButtonClicked = {
                        viewModel.setSiswa(it)
                        navController.navigate(Navigasi.Detail.name)
                    }
                )
            }

            // Halaman Detail Data Siswa
            composable(route = Navigasi.Detail.name) {
                TampilSiswa(
                    statusUiSiswa = uiState.value,
                    onBackButtonClicked = {
                        navController.popBackStack(
                            Navigasi.Formulirku.name,
                            inclusive = false
                        )
                    }
                )
            }
        }
    }
}
