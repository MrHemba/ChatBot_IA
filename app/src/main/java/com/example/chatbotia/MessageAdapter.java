package com.example.chatbotia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    // Constructor del adaptador
    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == Message.USER_MESSAGE) {
            // Cargar el diseño para el mensaje del usuario
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_user, parent, false);
        } else {
            // Cargar el diseño para el mensaje del chatbot
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_chatbot, parent, false);
        }
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        // Obtener el mensaje actual y configurar el texto
        Message message = messageList.get(position);
        holder.textMessage.setText(message.getText());
    }

    @Override
    public int getItemViewType(int position) {
        // Determinar si el mensaje es del usuario o del chatbot
        return messageList.get(position).getSender();
    }

    @Override
    public int getItemCount() {
        // Retornar el número de mensajes en la lista
        return messageList.size();
    }

    // Clase interna para el ViewHolder
    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            // Encontrar el TextView donde se mostrará el texto del mensaje
            textMessage = itemView.findViewById(R.id.textMessage);
        }
    }
}
