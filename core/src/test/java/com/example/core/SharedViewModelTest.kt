package com.example.core
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.ui.viewModel.SharedViewModel
import com.example.domain.Factura
import com.example.domain.FacturaFiltroState
import com.example.domain.GetFacturasFiltradasUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


class SharedViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: SharedViewModel
    private lateinit var getFacturasFiltradasUseCase: GetFacturasFiltradasUseCase
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getFacturasFiltradasUseCase = mockk()
        viewModel = SharedViewModel(getFacturasFiltradasUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test updateFiltroState con filtro valido`() = runTest {
        val filtro = FacturaFiltroState("01/01/2022", "31/12/2022", 0.0, 100.0, listOf("Pagada"))
        viewModel.updateFiltroState(filtro)
        advanceUntilIdle()
        viewModel.facturasFiltradas.observeForever { }
        advanceUntilIdle()
        coVerify { getFacturasFiltradasUseCase(filtro) }
    }


    @Test
    fun `test updateFiltroState con filtro invalido`() = runTest {
        val filtro = FacturaFiltroState("", "", 0.0, 0.0, emptyList())
        viewModel.updateFiltroState(filtro)
        advanceUntilIdle()
        viewModel.facturasFiltradas.observeForever { }
        advanceUntilIdle()
        coVerify { getFacturasFiltradasUseCase(filtro) }
    }

    @Test
    fun `test fetchFacturasFiltradas con exito`() = runTest {
        val filtro = FacturaFiltroState("01/01/2022", "31/12/2022", 0.0, 100.0, listOf("Pagada"))
        val facturas = listOf(Factura("Pagada", 30.0, "23/02/2019"))
        coEvery { getFacturasFiltradasUseCase(filtro) } returns facturas
        viewModel.updateFiltroState(filtro)
        advanceUntilIdle()
        assertEquals(facturas, viewModel.facturasFiltradas.value)
    }

    @Test
    fun `test fetchFacturasFiltradas con error`() = runTest{
        val filtro = FacturaFiltroState("01/01/2022", "31/12/2022", 0.0, 100.0, listOf("Pagada"))
        coEvery { getFacturasFiltradasUseCase(filtro) } throws Exception("Error al filtrar las facturas")
        viewModel.updateFiltroState(filtro)
        advanceUntilIdle()
        assertEquals("Error al filtrar las facturas", viewModel.errorMsg.value)
    }

    @Test
    fun `test filtroState`() {
        val filtro = FacturaFiltroState("01/01/2022", "31/12/2022", 0.0, 100.0, listOf("Pagada"))
        viewModel.updateFiltroState(filtro)
        assertEquals(filtro, viewModel.filtroState.value)
    }

    @Test
    fun `test facturasFiltradas`() {
        val facturas = listOf(Factura("Pagada", 30.0, "23/02/2019"))
        viewModel._facturasFiltradas.value = facturas
        assertEquals(facturas, viewModel.facturasFiltradas.value)
    }

    @Test
    fun `test errorMsg`() {
        val errorMsg = "Error al filtrar las facturas"
        viewModel._errorMsg.value = errorMsg
        assertEquals(errorMsg, viewModel.errorMsg.value)
    }
}
