package com.networking;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpMethod {
    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private record HttpResult(int statusCode, String body) {
    }

    private HttpResult getMethod(String targetUrl) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(targetUrl))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new HttpResult(response.statusCode(), response.body());
        } catch (Exception e) {
            return new HttpResult(-1, "Error: " + e.getMessage());
        }
    }

    private HttpResult postMethod(String targetUrl, String jsonBody) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(targetUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new HttpResult(response.statusCode(), response.body());
        } catch (Exception e) {
            return new HttpResult(-1, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        var httpMethods = new HttpMethod();

        String getUrl = "https://jsonplaceholder.typicode.com/posts/1";
        HttpResult getResponse = httpMethods.getMethod(getUrl);
        System.out.println("GET Status Code: " + getResponse.statusCode);
        System.out.println("GET Response Body: " + getResponse.body);

        String postUrl = "https://jsonplaceholder.typicode.com/posts";
        String jsonBody = """
                {
                    "userId": 1000,
                    "title": "Java Sensei",
                    "body": "This is Exhilarating, Get Excited!"
                }
                """;
        HttpResult postResponse = httpMethods.postMethod(postUrl, jsonBody);
        System.out.println("POST Status Code: " + postResponse.statusCode);
        System.out.println("POST Response Body: " + postResponse.body);
    }
}