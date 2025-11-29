package com.socialapp.litback.service;

import com.socialapp.litback.dao.PostDao;
import com.socialapp.litback.model.Comment;
import com.socialapp.litback.model.Post;
import com.socialapp.litback.model.PostDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
  private final PostDao postDao;

  public PostService(PostDao postDao) {
    this.postDao = postDao;
  }

  public Optional<Post> getPost(String id) {
    return postDao.findById(id);
  }

  public List<Comment> getComments(String postId) {
    return postDao.findComments(postId);
  }

  public PostDetails create(Post post) {
    return postDao.create(post);
  }

  public PostDetails update(Post post) {
    return postDao.update(post);
  }

  public void delete(String id) {
    postDao.delete(id);
  }

  public Comment addComment(Comment comment) {
    return postDao.addComment(comment);
  }

  public void deleteComment(String commentId) {
    postDao.deleteComment(commentId);
  }

  public Post like(String postId, boolean like) {
    return postDao.like(postId, like);
  }

  public Post save(String postId, boolean save) {
    return postDao.save(postId, save);
  }
}
