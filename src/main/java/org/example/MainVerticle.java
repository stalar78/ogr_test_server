package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        // Создаем HTTP сервер
        HttpServer server = vertx.createHttpServer();

        // Обработчик для входящих запросов
        server.requestHandler(req -> {
            // Настройка CORS для всех запросов
            req.response()
                    .putHeader("Access-Control-Allow-Origin", "*")
                    .putHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                    .putHeader("Access-Control-Allow-Headers", "Content-Type");

            // Ответ на основной запрос
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Welcome!");
        });

        // Слушаем порт 8080
        server.listen(8080, result -> {
            if (result.succeeded()) {
                System.out.println("HTTP server started on port 8080");
            } else {
                System.out.println("Failed to start server: " + result.cause());
            }
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());

        vertx.createHttpServer().requestHandler(req -> {
            // Настройка CORS для всех запросов
            req.response()
                    .putHeader("Access-Control-Allow-Origin", "*")
                    .putHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                    .putHeader("Access-Control-Allow-Headers", "Content-Type");

            // Обработчик для /api/data
            if ("/api/data".equals(req.path())) {
                req.response()
                        .putHeader("content-type", "application/json")
                        .end("{\"message\": \"Data from backend\"}");
            } else {
                req.response().setStatusCode(404).end();
            }
        }).listen(8080);
    }
}
