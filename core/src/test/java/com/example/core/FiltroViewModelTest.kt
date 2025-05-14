package com.example.core

import com.example.core.ui.viewModel.FiltroViewModel
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FiltroViewModelTest {

    private lateinit var viewModel: FiltroViewModel

    @Before
    fun setup() {
        viewModel = FiltroViewModel()
    }

    @Test
    fun `test ActualizarFechaDesde con fecha valida`() {
        val fecha = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        viewModel.ActualizarFechaDesde(fecha)
        assertEquals(fecha, viewModel.fechaDesde)
        assertNull(viewModel._errorMsgDesde.value)
    }

    @Test
    fun `test ActualizarFechaDesde con fecha invalida`() {
        val fecha = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }.timeInMillis
        viewModel.ActualizarFechaDesde(fecha)
        assertNull(viewModel.fechaDesde)
        assertEquals("Debe seleccionar una fecha válida", viewModel._errorMsgDesde.value)
    }

    @Test
    fun `test ActualizarFechaHasta con fecha valida`() {
        val fecha = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        viewModel.ActualizarFechaHasta(fecha)
        assertEquals(fecha, viewModel.fechaHasta)
        assertNull(viewModel._errorMsgHasta.value)
    }

    @Test
    fun `test ActualizarFechaHasta con fecha invalida`() {
        val fecha = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }.timeInMillis
        viewModel.ActualizarFechaHasta(fecha)
        assertNull(viewModel.fechaHasta)
        assertEquals("Debe seleccionar una fecha válida", viewModel._errorMsgHasta.value)
    }

    @Test
    fun `test SelectorImporte`() {
        val valor = 10.0f
        viewModel.SelectorImporte(valor)
        assertEquals(valor, viewModel.sliderPosition)
    }

    @Test
    fun `test SelectorEstado`() {
        val index = 0
        val valor = true
        viewModel.SelectorEstado(index, valor)
        assertEquals(valor, viewModel.checkedState[index])
    }

    @Test
    fun `test ResetarFiltros`() {
        viewModel.fechaDesde = Calendar.getInstance().timeInMillis
        viewModel.fechaHasta = Calendar.getInstance().timeInMillis
        viewModel.sliderPosition = 10.0f
        viewModel.checkedState = listOf(true, false, true, false, true)
        viewModel.ResetarFiltros()
        assertNull(viewModel.fechaDesde)
        assertNull(viewModel.fechaHasta)
        assertEquals(0.0f, viewModel.sliderPosition)
        assertEquals(listOf(false, false, false, false, false), viewModel.checkedState)
    }

    @Test
    fun `test ConstruirFiltroState`() {
        val fechaDesde = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val fechaHasta = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        viewModel.fechaDesde = fechaDesde
        viewModel.fechaHasta = fechaHasta
        viewModel.sliderPosition = 10.0f
        viewModel.checkedState = listOf(true, false, true, false, true)
        val filtroState = viewModel.ConstruirFiltroState()
        val formatoFecha = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        assertEquals(formatoFecha.format(fechaDesde), filtroState.fechaDesde)
        assertEquals(formatoFecha.format(fechaHasta), filtroState.fechaHasta)
        assertEquals(0.0, filtroState.importeMin)
        assertEquals(10.0, filtroState.importeMax)
        assertEquals(listOf("Pagada", "Cuota fija", "Plan de pago"), filtroState.estado)
    }
}
