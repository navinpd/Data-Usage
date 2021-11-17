package com.mobile.data.data.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mobile.data.DataRelatedTestData
import com.mobile.data.data.remote.NetworkService
import com.mobile.data.data.remote.model.mobileData.DataApiModel
import com.mobile.data.data.remote.model.mobileData.DataModel
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class DataRepositoryTest : TestCase() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var networkService: NetworkService
    @Mock
    private lateinit var observer: Observer<DataModel>
    @Mock
    private var serverCall: Call<DataApiModel>? = null
    @Mock
    private var response: Response<DataApiModel>? = null

    private lateinit var dataRepository: DataRepository

    @Before
    public override fun setUp() {
        dataRepository = DataRepository(networkService)
        //Mocking
        `when`(networkService.getDataUsage()).thenReturn(serverCall)
        `when`(serverCall!!.enqueue(any())).then {
            (it.arguments[0] as Callback<DataApiModel>)
                .onResponse(serverCall, response)
        }
    }

    @Test
    fun `verify success data return`() {
        `when`(response!!.code()).thenReturn(200)
        `when`(response!!.isSuccessful).thenReturn(true)
        `when`(response!!.body()).thenReturn(DataRelatedTestData.dataApiModel)

        //API call
        dataRepository.getMobileData()
        dataRepository.mobileLiveData.observeForever(observer)

        //Validation
        val captor = ArgumentCaptor.forClass(DataModel::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())

            assertEquals(DataRelatedTestData.dataApiModel, value.dataApiModel)
        }
    }

    @Test
    fun `verify onResponse error`() {
        `when`(response!!.code()).thenReturn(404)
        `when`(response!!.isSuccessful).thenReturn(true)
        `when`(response!!.body()).thenReturn(DataRelatedTestData.dataApiModel)

        //API call
        dataRepository.getMobileData()
        dataRepository.mobileLiveData.observeForever(observer)

        //Validation
        val captor = ArgumentCaptor.forClass(DataModel::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())

            assertEquals(value.throwable?.message, "Error in network response 404")
        }
    }

    @Test
    fun `verify network error onFailure`() {
        val errorMessage = "Error in network response"
        `when`(serverCall!!.enqueue(any())).then {
            (it.arguments[0] as Callback<DataApiModel>)
                .onFailure(serverCall, Throwable(errorMessage))
        }

        `when`(response!!.code()).thenReturn(404)
        `when`(response!!.isSuccessful).thenReturn(false)

        //API call
        dataRepository.getMobileData()
        dataRepository.mobileLiveData.observeForever(observer)

        //Validation
        val captor = ArgumentCaptor.forClass(DataModel::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())

            assertEquals(errorMessage, value.throwable?.message)
        }
    }
}