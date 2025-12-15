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
  private final ProfileService profileService;

  public PostService(PostDao postDao, ProfileService profileService) {
    this.postDao = postDao;
    this.profileService = profileService;
  }

  private boolean isAdmin(String userId) {
    return userId != null && !userId.isBlank() && profileService.isAdmin(userId);
  }

  public Optional<Post> getPost(String id, String userId) {
    return postDao.findById(id, userId, isAdmin(userId));
  }

  public Optional<PostDetails> getPostDetails(String id, String userId) {
    return postDao.findDetailsById(id, userId, isAdmin(userId));
  }

  public List<Post> listPosts(String userId) {
    return postDao.listAll(userId, isAdmin(userId));
  }

  public List<Post> listPostsByAuthor(String authorId, String userId) {
    boolean includeBanned = isAdmin(userId) || (userId != null && userId.equals(authorId));
    return postDao.listByAuthor(authorId, userId, includeBanned);
  }

  public List<Post> search(String query, String userId) {
    return postDao.search(query, userId, isAdmin(userId));
  }

  public List<Comment> getComments(String postId) {
    return postDao.findComments(postId);
  }

  public PostDetails create(Post post) {
    return postDao.create(post, null);
  }

  public PostDetails createWithImages(Post post, List<String> imageUrls, String caption) {
    return postDao.createWithImages(post, imageUrls, caption);
  }

  public PostDetails update(Post post, String caption) {
    return postDao
        .update(post, caption)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ERR_POST_NOT_FOUND));
  }

  public PostDetails updateWithImages(Post post, List<String> imageUrls, String caption) {
    return postDao
        .updateWithImages(post, imageUrls, caption)
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
    return postDao.randomPosts(limit, userId, isAdmin(userId));
  }
}
