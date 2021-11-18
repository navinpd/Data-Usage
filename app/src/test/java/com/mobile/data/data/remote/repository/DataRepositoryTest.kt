package com.mobile.data.data.remote.repository

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mobile.common.AssetFileLoader
import com.mobile.common.JsonParser
import com.mobile.data.DataRelatedTestData
import com.mobile.data.data.remote.NetworkService
import com.mobile.data.data.remote.model.mobileData.DataApiModel
import com.mobile.data.data.remote.model.mobileData.DataModel
import com.mobile.data.presentation.viewmodel.DataViewState
import com.mobile.data.presentation.viewmodel.UsedDataViewModel
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.Answer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.mockito.ArgumentMatchers.anyString

import org.mockito.Mockito.doAnswer
import org.mockito.invocation.InvocationOnMock
import org.mockito.kotlin.*


@RunWith(MockitoJUnitRunner::class)
class DataRepositoryTest : TestCase() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var networkService: NetworkService
    @Mock
    private var serverCall: Call<DataApiModel>? = null
    @Mock
    private lateinit var usedDataViewModel: UsedDataViewModel
    @Mock
    private var response: Response<DataApiModel>? = null
    @Mock
    private lateinit var jsonParser: JsonParser
    @Mock
    private lateinit var assetFileLoader: AssetFileLoader
    @Mock
    private lateinit var application: Application

    private lateinit var dataRepository: DataRepository

    @Before
    public override fun setUp() {
        dataRepository = DataRepository(networkService, jsonParser, assetFileLoader, application)
        //Mocking
        `when`(networkService.getDataUsage()).thenReturn(serverCall)
        `when`(serverCall!!.enqueue(any())).then {
            (it.arguments[0] as Callback<DataApiModel>)
                .onResponse(serverCall, response)
        }
    }

    @Test
    fun `verify success data return`() {
        //Mocking
        `when`(response!!.code()).thenReturn(200)
        `when`(response!!.isSuccessful).thenReturn(true)
        `when`(response!!.body()).thenReturn(DataRelatedTestData.dataApiModel)

        //API call
        dataRepository.getMobileData(usedDataViewModel)

        //Verify
        verify(usedDataViewModel, times(1))
            .updateUsedDataFromRepository(any())

//        `when`(usedDataViewModel.updateUsedDataFromRepository(any()))
//            .thenAnswer{ argumentCaptor<DataModel>()
//                it.getArgument(0)
//            }
//
//        `when`(usedDataViewModel.updateUsedDataFromRepository(any())).then {
//            assertEquals((it.arguments[0] as DataModel).dataApiModel,
//                DataRelatedTestData.dataApiModel)
//        }
    }

    @Test
    fun `verify onResponse error`() {
        //Mocking
        `when`(response!!.code()).thenReturn(404)
        `when`(response!!.isSuccessful).thenReturn(true)
        `when`(response!!.body()).thenReturn(DataRelatedTestData.dataApiModel)

        //API call
        dataRepository.getMobileData(usedDataViewModel)

        //Validation
        verify(usedDataViewModel, times(1))
            .updateUsedDataFromRepository(any())
//        `when`(usedDataViewModel.updateUsedDataFromRepository(any())).then {
//            assertEquals((it.arguments[0] as Throwable).message,
//                "Error in network response 404")
//        }
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
        dataRepository.getMobileData(usedDataViewModel)

        //Validation
        verify(usedDataViewModel, times(1))
            .updateUsedDataFromRepository(any())
//        `when`(usedDataViewModel.updateUsedDataFromRepository(any())).then {
//            assertEquals((it.arguments[0] as Throwable).message,
//                errorMessage)
//        }
    }
}