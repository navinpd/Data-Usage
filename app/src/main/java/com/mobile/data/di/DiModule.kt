package com.mobile.data.di

import android.content.Context
import com.mobile.data.data.remote.NetworkService
import com.mobile.data.data.remote.Networking
import com.mobile.data.data.remote.repository.DataRepository
import com.mobile.data.presentation.mapper.DataResultDomainMapper
import com.mobile.data.presentation.mapper.FieldsDomainMapper
import com.mobile.data.presentation.mapper.LinksDomainMapper
import com.mobile.data.presentation.mapper.RecordsDomainMapper
import com.mobile.data.presentation.mapper.ResultDomainMapper
import com.mobile.data.presentation.viewmodel.UsedDataViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DiModule {

    @Provides
    fun provideLinksDomainMapper() = LinksDomainMapper()

    @Provides
    fun provideFieldsDomainMapper() = FieldsDomainMapper()

    @Provides
    fun provideRecordsDomainMapper() = RecordsDomainMapper()

    @Provides
    fun provideNetworking(): NetworkService = Networking.createNetworking()

    @Provides
    fun provideDataRepository(networkService: NetworkService) = DataRepository(networkService)

    @Provides
    fun provideSharedPreference(@ApplicationContext appContext: Context) =
        appContext.getSharedPreferences("Local-Shared-Pref", 0)

    @Provides
    fun provideDataResultDomainMapper(
        resultDomainMapper: ResultDomainMapper,
    ) = DataResultDomainMapper(resultDomainMapper = resultDomainMapper)

    @Provides
    fun provideResultDomainMapper(
        fieldsDomainMapper: FieldsDomainMapper,
        recordsDomainMapper: RecordsDomainMapper,
        linksDomainMapper: LinksDomainMapper,
    ) = ResultDomainMapper(fieldsDomainMapper = fieldsDomainMapper,
        recordsDomainMapper = recordsDomainMapper,
        linksDomainMapper = linksDomainMapper)

    @Provides
    fun provideSearchViewModel(
        repository: DataRepository,
        dataResultDomainMapper: DataResultDomainMapper,
    ) = UsedDataViewModel(repository, dataResultDomainMapper)

}