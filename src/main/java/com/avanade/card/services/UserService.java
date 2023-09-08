package com.avanade.card.services;

import com.avanade.card.exceptions.UserFetchException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Service
public class UserService {
    private final String USER_API_URL = "";

    public Object fetchUserInfo(UUID userId) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();

        var request = HttpRequest
                .newBuilder(URI.create(USER_API_URL + userId))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new UserFetchException("Failed to fetch user information. Status code: " + response.statusCode());
        }
    }


}
