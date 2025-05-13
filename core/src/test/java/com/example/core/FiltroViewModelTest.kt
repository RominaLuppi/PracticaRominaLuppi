package com.example.core

import com.example.core.ui.viewModel.FiltroViewModel
import com.example.domain.FacturaFiltroState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class FiltroViewModelTest {

    private lateinit var viewModel: FiltroViewModel
    private lateinit var sdf: SimpleDateFormat

    @Before
    fun setup() {
        viewModel = FiltroViewModel()
        sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    }

    @Test
    fun `ActualizarFechaDesde válida`() {
        val fechaPasada = sdf.parse("01/01/2023")!!.time
        viewModel.ActualizarFechaDesde(fechaPasada)
        assertEquals(fechaPasada, viewModel.fechaDesde)
        assertNull(viewModel.errorMsgDesde.value)
    }

    @Test
    fun `ActualizarFechaDesde inválida futura`() {
        val fechaFutura = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis
        viewModel.ActualizarFechaDesde(fechaFutura)
        assertNotNull(viewModel.errorMsgDesde.value)
        assertNull(viewModel.fechaDesde)
    }

    @Test
    fun `ActualizarFechaHasta válida`() {
        val fechaPasada = sdf.parse("01/01/2023")!!.time
        viewModel.ActualizarFechaHasta(fechaPasada)
        assertEquals(fechaPasada, viewModel.fechaHasta)
        assertNull(viewModel.errorMsgHasta.value)
    }

    @Test
    fun `ActualizarFechaHasta inválida futura`() {
        val fechaFutura = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis
        viewModel.ActualizarFechaHasta(fechaFutura)
        assertNotNull(viewModel.errorMsgHasta.value)
        assertNull(viewModel.fechaHasta)
    }

    @Test
    fun `SelectorImporte actualiza correctamente`() {
        viewModel.SelectorImporte(50f)
        assertEquals(50f, viewModel.sliderPosition)
    }

    @Test
    fun `SelectorEstado modifica estado correctamente`() {
        viewModel.SelectorEstado(2, true)
        assertTrue(viewModel.checkedState[2])
    }

    @Test
    fun `ResetarFiltros reinicia todos los campos`() {
        viewModel.SelectorImporte(30f)
        viewModel.SelectorEstado(0, true)
        viewModel.ActualizarFechaDesde(sdf.parse("01/01/2022")!!.time)
        viewModel.ResetarFiltros()
        assertEquals(0f, viewModel.sliderPosition)
        assertTrue(viewModel.checkedState.all { !it })
        assertNull(viewModel.fechaDesde)
        assertNull(viewModel.fechaHasta)
    }

    @Test
    fun `ConstruirFiltroState genera el filtro correctamente`() {
        viewModel.SelectorImporte(75f)
        viewModel.SelectorEstado(1, true) // Anulada
        viewModel.ActualizarFechaDesde(sdf.parse("01/01/2022")!!.time)
        viewModel.ActualizarFechaHasta(sdf.parse("31/12/2023")!!.time)
        val filtro = viewModel.ConstruirFiltroState()
        assertEquals("Anulada", filtro.estado.first())
        assertEquals(0.0, filtro.importeMin, 0.01)
        assertEquals(75.0, filtro.importeMax, 0.01)
        assertEquals("01/01/2022", filtro.fechaDesde)
        assertEquals("31/12/2023", filtro.fechaHasta)
    }

    @Test
    fun `actualizarFiltro guarda correctamente el estado`() = runTest {
        val filtro = FacturaFiltroState("01/01/2023", "02/02/2024", 10.0, 100.0, listOf("Pagada"))
        viewModel.actualizarFiltro(filtro)
        assertEquals(filtro, viewModel.filtro.value)
    }

    @Test
    fun `mostrar y limpiar errores funcionan`() {
        viewModel.motrarMsgErrorFechaDesde("Error desde")
        assertEquals("Error desde", viewModel.errorMsgDesde.value)
        viewModel.limpiarMsgErrorFechaDesde()
        assertNull(viewModel.errorMsgDesde.value)

        viewModel.motrarMsgErrorFechaHasta("Error hasta")
        assertEquals("Error hasta", viewModel.errorMsgHasta.value)
        viewModel.limpiarMsgErrorFechaHasta()
        assertNull(viewModel.errorMsgHasta.value)

        viewModel.mostrarMsgError("Error general")
        assertEquals("Error general", viewModel.errorMsg.value)
        viewModel.limpiarMsgError()
        assertNull(viewModel.errorMsg.value)
    }
}
