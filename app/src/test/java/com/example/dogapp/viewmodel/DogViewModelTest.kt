import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dogapp.viewmodel.DogViewModel
import com.example.dogimageloader.DogImageLib
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@OptIn(ExperimentalCoroutinesApi::class)
class DogViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var dogImageLib: DogImageLib

    private lateinit var testDispatcher: TestCoroutineDispatcher
    private lateinit var viewModel: DogViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testDispatcher)
        viewModel = DogViewModel(dogImageLib)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test fetchFirstDog`() = testDispatcher.runBlockingTest {
        val imageUrl = "url1"
        `when`(dogImageLib.getImage()).thenReturn(imageUrl)

        viewModel.fetchFirstDog()

        assert(viewModel.currentDog.value == imageUrl)
    }

    @Test
    fun `test loadNextDog`() = testDispatcher.runBlockingTest {
        val imageUrl = "url2"
        `when`(dogImageLib.getNextImage()).thenReturn(imageUrl)

        viewModel.loadNextDog()

        assert(viewModel.currentDog.value == imageUrl)
    }

    @Test
    fun `test loadPreviousDog`() = testDispatcher.runBlockingTest {
        val imageUrl = "url3"
        `when`(dogImageLib.getPreviousImage()).thenReturn(imageUrl)

        viewModel.loadPreviousDog()

        assert(viewModel.currentDog.value == imageUrl)
    }

    @Test
    fun `test fetchAndSaveMultipleDogs`() = testDispatcher.runBlockingTest {
        val count = "5"
        `when`(dogImageLib.getImages(count.toInt())).thenReturn(listOf())

        viewModel.fetchAndSaveMultipleDogs(count)
    }
}
