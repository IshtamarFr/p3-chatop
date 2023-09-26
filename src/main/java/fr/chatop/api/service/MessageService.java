package fr.chatop.api.service;

import fr.chatop.api.model.Message;

public interface MessageService {
    Message saveMessage(Message message);
}
