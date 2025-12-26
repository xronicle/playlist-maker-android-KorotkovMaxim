package com.example.playlistmaker.data

import com.example.playlistmaker.data.dto.BaseResponse

interface NetworkClient {
    fun doRequest(request: Any): BaseResponse
}