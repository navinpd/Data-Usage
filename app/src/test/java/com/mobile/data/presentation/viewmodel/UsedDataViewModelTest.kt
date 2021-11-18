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
    private var annualResultMapper = AnnualResultMapper()

    @Before
    public override fun setUp() {
        val dataDomainMapper =
            DataResultDomainMapper(ResultDomainMapper(fieldsDomainMapper = FieldsDomainMapper(),
                recordsDomainMapper = RecordsDomainMapper(),
                linksDomainMapper = LinksDomainMapper()))

        usedDataViewModel = UsedDataViewModel(dataRepository, dataDomainMapper, annualResultMapper)
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
            val records = DataViewState.ShowData(annualResultMapper.map(DataRelatedTestData.result.records))

            assertEquals(records.show[0].volumeRecord, (value as DataViewState.ShowData).show[0].volumeRecord)
            assertEquals(records.show[0].year, (value as DataViewState.ShowData).show[0].year)
        }
    }

    @Test
    fun `verify error scenario`() {
        usedDataViewModel.dataViewState.observeForever(observer)
        var message = "Network Error"
        //Mocking
        `when`(dataRepository.getMobileData(usedDataViewModel)).then(Answer {
            usedDataViewModel.updateUsedDataFromRepository(
                DataModel(null, Throwable(message))
            )
        })

        // actual API call
        usedDataViewModel.requestUsedData()

        val captor = ArgumentCaptor.forClass(DataViewState::class.java)
        captor.run {
            verify(observer, times(3)).onChanged(capture())
            val records = DataViewState.ShowError(message)

            assertEquals(records.message, (value as DataViewState.ShowError).message)
        }
    }
}