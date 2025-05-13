package com.example.core

import com.example.core.ui.viewModel.FacturaViewModel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.database.FacturasDao
import com.example.data.repository.GetFacturasUseCaseData
import com.example.domain.Factura
import com.example.domain.FacturaFiltroState
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.Assert.assertNotNull


@ExperimentalCoroutinesApi
class FacturaViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FacturaViewModel
    private val getFacturasUseCaseData = mockk<GetFacturasUseCaseData>()
    private val facturasDao = mockk<FacturasDao>(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { getFacturasUseCaseData() } returns listOf(
            Factura("01/01/2023", 15.0, "Pagada"),
            Factura("15/01/2023", 25.0, "Pendiente de pago")
        )
        viewModel = FacturaViewModel(getFacturasUseCaseData, facturasDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cargarFacturas carga correctamente las facturas`() = runTest {
        viewModel.cargarFacturas()
        advanceUntilIdle()

        val result = viewModel.factura.value
        assertEquals(2, result?.size)
        assertEquals(25.0, viewModel.maxImporte.value)
    }

    @Test
    fun `formatearFecha formatea correctamente`() {
        val resultado = viewModel.formatearFecha("25/12/2023")
        assertEquals("25 Dic 2023", resultado)
    }

    @Test
    fun `filtrarFacturas devuelve resultados correctos`() {
        val facturas = listOf(
            Factura("Pagada", 15.0, "01/01/2023"),
            Factura("Pendiente de pago", 9.0, "15/01/2022"),
            Factura("Pendiente de pago", 27.0, "16/01/2023"),
            Factura("Pendiente de pago", 25.0, ""),
            Factura("Pendiente de pago", 0.0 ,"10/01/2023")
        )
        val filtro = FacturaFiltroState(
            fechaDesde = "10/01/2023",
            fechaHasta = "15/01/2023",
            importeMin = 10.0,
            importeMax = 25.0,
            estado = listOf("Pendiente de pago")
        )

        val resultado = viewModel.filtrarFacturas(facturas, filtro)
        assertEquals(1, resultado.size)
        assertEquals("Pendiente de pago", resultado.first().descEstado)
    }

    @Test
    fun `actualizarFacturas actualiza correctamente`() {
        val nuevasFacturas = listOf(
            Factura("Pagada", 30.0, "02/02/2023"),
            Factura("Cuota Fija", 30.0, "02/02/2023"),
            Factura("Pagada", 10.0, ""),
            Factura("", 50.0, "02/02/2023"))

        viewModel.actualizarFacturas(nuevasFacturas)
        val facturaSinFecha = viewModel.factura.value?.find { it.fecha.isBlank() }

        assertEquals(4, viewModel.factura.value?.size)
        assertEquals(30.0, viewModel.factura.value?.first()?.importeOrdenacion)
        assertEquals(nuevasFacturas, viewModel.factura.value)
        assertNotNull(facturaSinFecha)
        assertEquals(10.0, facturaSinFecha?.importeOrdenacion)
    }

    @Test
    fun `resetFacturas restablece correctamente`() = runTest {
        viewModel.cargarFacturas()
        advanceUntilIdle()
        viewModel.resetFacturas()
        assertEquals(2, viewModel.factura.value?.size)
    }

    @Test
    fun `clearAllDatabase llama al dao`() = runTest {
        coEvery { facturasDao.deleteAllDatabase() } just Runs
        viewModel.clearAllDatabase()
        coVerify { facturasDao.deleteAllDatabase() }
    }
}


