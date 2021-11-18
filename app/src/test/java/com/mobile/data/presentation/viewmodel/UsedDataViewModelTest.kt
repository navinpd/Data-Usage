package com.mobile.data.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mobile.data.DataRelatedTestData
import com.mobile.data.data.remote.model.mobileData.DataModel
import com.mobile.data.data.remote.repository.DataRepository
import com.mobile.data.presentation.mapper.*
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.stubbing.Answer

@RunWith(MockitoJUnitRunner::class)
internal class UsedDataViewModelTest : TestCase() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    private var observer: Observer<DataViewState> =
        mock(Observer::class.java) as Observer<DataViewState>

    private lateinit var usedDataViewModel: UsedDataViewModel

    @Before
    public override fun setUp() {
        val dataDomainMapper =
            DataResultDomainMapper(ResultDomainMapper(fieldsDomainMapper = FieldsDomainMapper(),
                recordsDomainMapper = RecordsDomainMapper(),
                linksDomainMapper = LinksDomainMapper()))

        usedDataViewModel = UsedDataViewModel(dataRepository, dataDomainMapper)
    }

    @Test
    fun `verify successful result`() {
        usedDataViewModel.dataViewState.observeForever(observer)
        //Mocking
        `when`(dataRepository.getMobileData(usedDataViewModel)).then(Answer {
            usedDataViewModel.updateUsedDataFromRepository(
                DataModel(DataRelatedTestData.dataApiModel, null)
            )
        })

        // actual API call
        usedDataViewModel.requestUsedData()

        val captor = ArgumentCaptor.forClass(DataViewState::class.java)
        captor.run {
            verify(observer, times(3)).onChanged(capture())
            val records = DataViewState.ShowData(DataRelatedTestData.result.records)

            assertEquals(records.show[0].id, (value as DataViewState.ShowData).show[0].id)
            assertEquals(records.show[0].quarter, (value as DataViewState.ShowData).show[0].quarter)
            assertEquals(records.show[0].year, (value as DataViewState.ShowData).show[0].year)
            assertEquals(records.show[0].volumeRecords, (value as DataViewState.ShowData).show[0].volumeRecords)
        }
    }

    @Test
    fun `verify error scenario`() {
        usedDataViewModel.dataViewState.observeForever(observer)
        //Mocking
        `when`(dataRepository.getMobileData(usedDataViewModel)).then(Answer {
            usedDataViewModel.updateUsedDataFromRepository(
                DataModel(null, Throwable("Network Error"))
            )
        })

        // actual API call
        usedDataViewModel.requestUsedData()

        val captor = ArgumentCaptor.forClass(DataViewState::class.java)
        captor.run {
            verify(observer, times(3)).onChanged(capture())
            val records = DataViewState.ShowError()

            assertEquals(records.message, (value as DataViewState.ShowError).message)
        }
    }
}