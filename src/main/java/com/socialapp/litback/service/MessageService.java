package com.socialapp.litback.service;

import com.socialapp.litback.dao.MessageDao;
import com.socialapp.litback.model.Conversation;
import com.socialapp.litback.model.DirectMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
  private final MessageDao messageDao;

  public MessageService(MessageDao messageDao) {
    this.messageDao = messageDao;
  }

  public List<DirectMessage> getThread(String conversationId) {
    return messageDao.getThread(conversationId);
  }

  public DirectMessage send(String conversationId, String from, String to, String text) {
    return messageDao.sendMessage(conversationId, from, to, text);
  }

  public void deleteMessage(String id) {
    messageDao.deleteMessage(id);
  }

  public Conversation createConversation(String a, String b) {
    return messageDao.createConversation(a, b);
  }

  public List<Conversation> listConversations(String userId) {
    return messageDao.listConversations(userId);
  }
}
