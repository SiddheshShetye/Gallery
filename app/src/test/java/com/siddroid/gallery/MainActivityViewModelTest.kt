package com.siddroid.gallery

import com.siddroid.gallery.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

    private val repository: GalleryRepository = mock()
    private val mapper: StateMapper = mock()
    private val connectivityStatus: ConnectivityStatus = mock()
    private val viewModel by lazy { MainActivityViewModel(repository, mapper, connectivityStatus) }

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }


    @Test
    fun testGetImageFlow() {
        runTest {
                whenever(repository.getImageList()).thenReturn(listOf(ImageModel(url = "google.com", hdurl = "www.hdurl.com")))
                val job = viewModel.gridDataFlow.onEach {
                    Assert.assertEquals(GridViewState(listOf(GridItem(url = "google.com"))), it)
                }
                    .launchIn(this)
                viewModel.getImages()

                verify(repository, times(1)).getImageList()
                verify(
                    mapper,
                    times(1)
                ).mapImageModelToGridViewState(listOf(ImageModel(url = "google.com", hdurl = "www.hdurl.com")))
             job.cancel()
        }
    }

    @Test
    fun `test get details flow`() {
        runTest {
            testGetImageFlow()
            val job = viewModel.detailsDataStateFlow.onEach {
                Assert.assertEquals(
                    PhotoDetailsViewState(listOf(PhotoDetailItem(photoUrl = "www.hdulr.com"))),
                    it
                )
            }
                .launchIn(this)
            viewModel.getDetails()
            verify(mapper, times(1)).mapImageModelToDetailsViewState(listOf(ImageModel(url = "google.com", hdurl = "www.hdurl.com")))
            job.cancel()
        }

    }

}