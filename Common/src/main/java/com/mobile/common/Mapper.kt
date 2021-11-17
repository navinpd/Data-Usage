package com.mobile.common

interface Mapper<Param, Result> {

    fun map(from : Param): Result
}