package com.socialapp.litback.service;

import com.socialapp.litback.dao.PostDao;
import com.socialapp.litback.model.Comment;
import com.socialapp.litback.model.Post;
import com.socialapp.litback.model.PostDetails;
import com.socialapp.litback.shared.Constants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostService {
  private final PostDao postDao;

  public PostService(PostDao postDao) {
    this.postDao = postDao;
  }

  public Optional<Post> getPost(String id, String userId) {
    return postDao.findById(id, userId);
  }

  public Optional<PostDetails> getPostDetails(String id, String userId) {
    return postDao.findDetailsById(id, userId);
  }

  public List<Post> listPosts(String userId) {
    return postDao.listAll(userId);
  }

  public List<Post> listPostsByAuthor(String authorId, String userId) {
    return postDao.listByAuthor(authorId, userId);
  }

  public List<Post> search(String query, String userId) {
    return postDao.searchByCaption(query, userId);
  }

  public List<Comment> getComments(String postId) {
    return postDao.findComments(postId);
  }

  public PostDetails create(Post post) {
    return postDao.create(post);
  }

  public PostDetails createWithImages(Post post, List<String> imageUrls) {
    return postDao.createWithImages(post, imageUrls);
  }

  public PostDetails update(Post post) {
    return postDao
        .update(post)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_POST_NOT_FOUND));
  }

  public PostDetails updateWithImages(Post post, List<String> imageUrls) {
    return postDao
        .updateWithImages(post, imageUrls)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_POST_NOT_FOUND));
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

  public Post like(String postId, String userId, boolean like) {
    return postDao
        .like(postId, userId, like)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_POST_NOT_FOUND));
  }

  public Post save(String postId, boolean save) {
    return postDao
        .save(postId, save)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_POST_NOT_FOUND));
  }

  public Post ban(String postId, boolean banned) {
    return postDao
        .setBanned(postId, banned)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_POST_NOT_FOUND));
  }

  public List<Post> randomPosts(int limit, String userId) {
    return postDao.randomPosts(limit, userId);
  }
}
