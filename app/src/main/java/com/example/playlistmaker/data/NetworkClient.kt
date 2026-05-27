package com.example.playlistmaker.data

import com.example.playlistmaker.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}