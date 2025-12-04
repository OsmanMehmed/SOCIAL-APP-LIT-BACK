package com.socialapp.litback;

import com.socialapp.litback.model.Conversation;
import com.socialapp.litback.model.DirectMessage;
import com.socialapp.litback.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MessageFlowTest {

  @Autowired
  private MessageService messageService;

  @Test
  void sendingMessageIsPersistedInConversation() {
    Conversation conversation = messageService.createConversation("user-2", "user-1");
    DirectMessage message = messageService.send(conversation.id(), "user-2", "user-1", "Persisted hello");

    Conversation reused = messageService.createConversation("user-2", "user-1");
    assertThat(reused.id()).isEqualTo(conversation.id());

    List<DirectMessage> thread = messageService.getThread(conversation.id());
    assertThat(thread).isNotEmpty();
    assertThat(thread.stream().map(DirectMessage::id)).contains(message.id());
  }
}
