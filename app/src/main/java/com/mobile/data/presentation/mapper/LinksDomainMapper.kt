package com.mobile.data.presentation.mapper

import com.mobile.common.Mapper
import com.mobile.data.data.remote.model.mobileData.DataApiModel
import com.mobile.data.data.remote.model.mobileData.LinksApiModel
import com.mobile.data.presentation.model.Links
import javax.inject.Inject

internal class LinksDomainMapper @Inject constructor() : Mapper<LinksApiModel, Links> {
    override fun map(from: LinksApiModel): Links {
        return Links(
            start = from.start,
            next = from.next
        )
    }
}