package main.model.binance.websocket;

/* ============================================================
 * java-test.resources.model.binance-api
 * https://github.com/webcerebrium/java-binance-api
 * ============================================================
 * Copyright 2017-, Viktor Lopata, Web Cerebrium OÜ
 * Released under the MIT License
 * ============================================================ */


import com.webcerebrium.binance.datatype.BinanceEventDepthUpdate;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import com.webcerebrium.binance.api.BinanceApiException;
import com.webcerebrium.binance.api.BinanceApi;
import org.eclipse.jetty.websocket.api.Session;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import org.slf4j.Logger;


//@Slf4j
public abstract class BinanceWebSocketAdapterDepth extends WebSocketAdapter {

    private static final Logger log = LoggerFactory.getLogger(BinanceWebSocketAdapterDepth.class);

    @Override
    public void onWebSocketConnect(Session sess) {
        log.debug("onWebSocketConnect: {}", sess);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        log.error("onWebSocketError: {}", cause);
    }

    @Override
    public void onWebSocketText(String message) {
        log.debug("onWebSocketText message={}", message);
        JsonObject operation = (new Gson()).fromJson(message, JsonObject.class);
        try {
            onMessage(new BinanceEventDepthUpdate(operation));
        } catch ( BinanceApiException e ) {
            log.error("Error in websocket message {}", e.getMessage());
        }
    }

    public abstract void onMessage(BinanceEventDepthUpdate event) throws BinanceApiException;
}