package com.example.lawny_proj.modules

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

import org.json.JSONObject

class WebSocketInterface : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send("App Connected")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val response = JSONObject(text)
        val event = response.getString("event")
        val data = response.getJSONObject("data")

        print("EVENT: $event")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.send("Closing connection: $reason")
        webSocket.close(1000, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response) {
        print ("Failure: $response")
    }
}