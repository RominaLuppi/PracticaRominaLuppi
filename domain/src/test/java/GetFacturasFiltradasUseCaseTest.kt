
import com.example.domain.Factura
import com.example.domain.FacturaFiltroState
import com.example.domain.GetFacturasFiltradasUseCase
import com.example.domain.repository.FacturaRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetFacturasFiltradasUseCaseTest {


    private lateinit var useCase: GetFacturasFiltradasUseCase
    private lateinit var repository: FacturaRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        useCase = GetFacturasFiltradasUseCase(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test invoke con filtro valido`() = runTest {
        val filtro = FacturaFiltroState("01/01/2022", "31/12/2022", 0.0, 100.0, listOf("Pagada"))
        val facturas = listOf(Factura("Pagada", 25.0, "05/01/2022"))
        coEvery { repository.getFacturasFiltradas(filtro) } returns facturas
        val result = useCase(filtro)
        coVerify { repository.getFacturasFiltradas(filtro) }
        advanceUntilIdle()
        assertEquals(facturas, result)
    }

    @Test
    fun `test invoke con filtro invalido`() = runTest {
        val filtro = FacturaFiltroState("", "", 0.0, 0.0, emptyList())
        val facturas = listOf(Factura("Pendiente de pago", 45.0, "03/01/2022" ))
        coEvery { repository.getFacturasFiltradas(filtro) } returns facturas
        val result = useCase(filtro)
        coVerify { repository.getFacturasFiltradas(filtro) }
        assertEquals(facturas, result)
    }

    @Test
    fun `test invoke con repository que devuelve null`() = runTest {
        val filtro = FacturaFiltroState("01/01/2022", "31/12/2022", 0.0, 100.0, listOf("Pagada"))
        coEvery { repository.getFacturasFiltradas(filtro) } returns null
        val result = useCase(filtro)
        coVerify { repository.getFacturasFiltradas(filtro) }
        assertEquals(emptyList(), result)
    }

    @Test
    fun `test invoke con repository que devuelve lista vacia`() = runTest {
        val filtro = FacturaFiltroState("01/01/2022", "31/12/2022", 0.0, 100.0, listOf("Pagada"))
        coEvery { repository.getFacturasFiltradas(filtro) } returns emptyList()
        val result = useCase(filtro)
        coVerify { repository.getFacturasFiltradas(filtro) }
        assertEquals(emptyList(), result)
    }
}
