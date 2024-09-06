package com.example.chatbotia;

import com.example.chatbotia.CohereResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CohereApiService {
    @POST("generate")
    @Headers("Content-Type: application/json")
    Call<CohereResponse> generateText(
            @Header("Authorization") String apiKey,
            @Body RequestBody requestBody
    );
}
