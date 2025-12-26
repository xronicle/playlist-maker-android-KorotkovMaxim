package com.example.playlistmaker.data.network

import com.example.playlistmaker.creator.Storage
import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.dto.BaseResponse
import com.example.playlistmaker.data.dto.TrackSearchRequest
import com.example.playlistmaker.data.dto.TrackSearchResponse

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {
    override fun doRequest(request: Any): BaseResponse {
        val searchList = storage.search((request as TrackSearchRequest).expression)
        return TrackSearchResponse(searchList).apply { resultCode = 200 }
    }
}