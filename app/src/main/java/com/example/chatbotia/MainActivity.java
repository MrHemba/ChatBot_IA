package com.example.chatbotia;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String API_BASE_URL = "https://api.cohere.ai/v1/";
    private static final String API_KEY = "Bearer oEYde2abkHznWur30r87WmPxVvRGroEuajwVQOh2"; // Usa tu clave API de Cohere

    private EditText editTextUserMessage;
    private RecyclerView recyclerViewMessages;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de las vistas
        editTextUserMessage = findViewById(R.id.editTextUserMessage);
        ImageButton buttonSend = findViewById(R.id.buttonSend);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);

        // Inicializar la lista de mensajes y el adaptador
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);

        // Configuración del botón para enviar el mensaje
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = editTextUserMessage.getText().toString().trim();
                if (!userMessage.isEmpty()) {
                    addMessage(userMessage, Message.USER_MESSAGE); // Agregar mensaje del usuario
                    editTextUserMessage.setText(""); // Limpiar el campo de entrada
                    generateText(userMessage);  // Llamada a la API para generar el texto
                }
            }
        });
    }

    private void generateText(String userMessage) {
        // Crear el cuerpo de la solicitud
        String jsonPayload = "{\n" +
                "  \"model\": \"command-xlarge-nightly\",\n" +
                "  \"prompt\": \"" + userMessage + "\",\n" +
                "  \"max_tokens\": 100\n" +
                "}";

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                jsonPayload
        );

        // Instanciar Retrofit y hacer la solicitud
        CohereApiService cohereApiService = RetrofitClient.getClient(API_BASE_URL)
                .create(CohereApiService.class);

        Call<CohereResponse> call = cohereApiService.generateText(API_KEY, requestBody);
        call.enqueue(new Callback<CohereResponse>() {
            @Override
            public void onResponse(Call<CohereResponse> call, Response<CohereResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String generatedText = response.body().getGenerations().get(0).getText();
                    addMessage(generatedText, Message.CHATBOT_MESSAGE); // Agregar respuesta del chatbot
                } else {
                    Log.e("CohereAPI", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CohereResponse> call, Throwable t) {
                Log.e("CohereAPI", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

    // Método para agregar mensajes a la conversación
    private void addMessage(String messageText, int sender) {
        messageList.add(new Message(messageText, sender));
        messageAdapter.notifyItemInserted(messageList.size() - 1); // Notificar al adaptador
        recyclerViewMessages.scrollToPosition(messageList.size() - 1); // Desplazarse al final
    }
}
