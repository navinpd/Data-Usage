package com.mobile.data.presentation.viewmodel

import android.content.SharedPreferences
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
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.stubbing.Answer

@RunWith(MockitoJUnitRunner::class)
internal class UsedDataViewModelTest : TestCase() {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    @Mock
    private lateinit var editor: SharedPreferences.Editor

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

        `when`(sharedPreferences.edit()).thenReturn(editor)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)

        usedDataViewModel = UsedDataViewModel(dataRepository, dataDomainMapper, annualResultMapper, sharedPreferences)
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

    @Test
    fun `verify failed getLocalRecords`() {
        `when`(sharedPreferences.getString(anyString(), anyString()))
            .thenReturn("")
        val itemLists = usedDataViewModel.getLocalRecords()
        assertEquals(itemLists.size, 0)
    }

    @Test
    fun `verify successful getLocalRecords`() {
        //Mocking
        `when`(sharedPreferences.getString(anyString(), anyString()))
            .thenReturn(DataRelatedTestData.jsonRecords)
        //api call
        val itemLists = usedDataViewModel.getLocalRecords()
        //validation
        assertEquals(itemLists.size, 3)
        assertEquals(itemLists[0].year, "2004")
    }

    @Test
    fun `verify Success getQuarterlyData`() {
        //Mocking
        `when`(sharedPreferences.getString(anyString(), anyString()))
            .thenReturn(DataRelatedTestData.jsonRecords)

        //api call
        val itemLists = usedDataViewModel.getQuarterlyData("2004")

        //validation
        assertEquals(itemLists.size, 2)
        assertEquals(itemLists[0].year, "2004")
    }


    @Test
    fun `verify Failed getQuarterlyData`() {
        //api call
        val itemLists = usedDataViewModel.getQuarterlyData("")

        //validation
        assertEquals(itemLists.size, 0)
    }
}