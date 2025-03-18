package com.example.lms.chat;

import com.example.lms.users.UserDTO;
import com.example.lms.users.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private final UserService userService;

    public ChatController(UserService userService) {
        this.userService = userService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        UserDTO user = userService.getUserById(chatMessage.getSenderId()); // Fetch user by ID

        if (user != null) {
            chatMessage.setSenderName(user.getFirstName()); // Set first name
            chatMessage.setProfileImageUrl(user.getProfileImageUrl()); // Attach profile image
        } else {
            chatMessage.setSenderName("Unknown");
            chatMessage.setProfileImageUrl("/default-avatar.png"); // Fallback image
        }

        return chatMessage;
    }


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        chatMessage.setContent(chatMessage.getSender() + " joined the chat");
        return chatMessage;
    }
}

