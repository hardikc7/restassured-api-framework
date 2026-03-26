package com.sdet.utils;

import io.restassured.response.Response;
import java.util.function.Supplier;

public class RetryUtil {

    private static final int MAX_RETRIES = 3;
    private static final long INITIAL_DELAY_MS = 1000;

    public static Response executeWithRetry(Supplier<Response> apiCall) {
        int attempts = 0;
        long delay = INITIAL_DELAY_MS;

        while (attempts < MAX_RETRIES) {
            try {
                Response response = apiCall.get();

                if (response.statusCode() != 429 && response.statusCode() < 500) {
                    return response;
                }

                System.out.println("Retry attempt " + (attempts + 1)
                    + " — status: " + response.statusCode()
                    + " — waiting " + delay + "ms");

            } catch (Exception e) {
                System.out.println("Retry attempt " + (attempts + 1)
                    + " — exception: " + e.getMessage());
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            delay *= 2;
            attempts++;
        }

        throw new RuntimeException("API call failed after " + MAX_RETRIES + " retries");
    }
}