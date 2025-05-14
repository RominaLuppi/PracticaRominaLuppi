import com.example.domain.Factura
import com.example.domain.GetFacturasUseCase
import com.example.domain.repository.FacturaRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetFacturasUseCaseTest {

    private lateinit var useCase: GetFacturasUseCase
    private lateinit var repository: FacturaRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetFacturasUseCase(repository)
    }

    @Test
    fun `test invoke con repository que devuelve lista de facturas`() = runTest {
        val facturas = listOf(Factura("Pendiente de pago", 87.0, "05/07/2019"))
        coEvery { repository.getAllFacturas() } returns facturas
        val result = useCase()
        coVerify { repository.getAllFacturas() }
        assertEquals(facturas, result)
    }

    @Test
    fun `test invoke con repository que devuelve null`() = runTest {
        coEvery { repository.getAllFacturas() } returns null
        val result = useCase()
        coVerify { repository.getAllFacturas() }
        assertNull(result)
    }

    @Test
    fun `test invoke con repository que devuelve lista vacia`() = runTest {
        coEvery { repository.getAllFacturas() } returns emptyList()
        val result = useCase()
        coVerify { repository.getAllFacturas() }
        assertEquals(emptyList(), result)
    }
}
