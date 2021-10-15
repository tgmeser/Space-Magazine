package com.babyapps.spacemagazine.util

import kotlinx.coroutines.flow.*


inline fun <ResultType, RequestType> networnBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()
    val flow = if (shouldFetch(data)) {
        //its new data ?
        emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (e: Exception) {
            query().map { Resource.Error(e.message.toString(), it) }

        }
    } else {
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}