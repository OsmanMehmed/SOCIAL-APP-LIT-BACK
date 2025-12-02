package com.socialapp.litback.controller;

import com.socialapp.litback.model.Comment;
import com.socialapp.litback.model.Post;
import com.socialapp.litback.model.PostDetails;
import com.socialapp.litback.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public ResponseEntity<List<Post>> listPosts() {
    return ResponseEntity.ok(postService.listPosts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPost(@PathVariable String id) {
    return postService.getPost(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<List<Comment>> getComments(@PathVariable String id) {
    return ResponseEntity.ok(postService.getComments(id));
  }

  @PostMapping
  public ResponseEntity<PostDetails> createPost(@RequestBody Post post) {
    return ResponseEntity.ok(postService.create(post));
  }

  @PutMapping("/{id}")
  public ResponseEntity<PostDetails> updatePost(@PathVariable String id, @RequestBody Post post) {
    return ResponseEntity.ok(postService.update(new Post(id, post.caption(), post.authorId(), post.likes(), post.comments(), post.saves(), post.banned())));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable String id) {
    postService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/comments")
  public ResponseEntity<Comment> addComment(@PathVariable String id, @RequestBody Comment comment) {
    return ResponseEntity.ok(postService.addComment(new Comment(comment.id(), id, comment.authorId(), comment.text(), comment.createdAt())));
  }

  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
    postService.deleteComment(commentId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/like")
  public ResponseEntity<Post> like(@PathVariable String id, @RequestParam(defaultValue = "true") boolean like) {
    return ResponseEntity.ok(postService.like(id, like));
  }

  @PostMapping("/{id}/save")
  public ResponseEntity<Post> save(@PathVariable String id, @RequestParam(defaultValue = "true") boolean save) {
    return ResponseEntity.ok(postService.save(id, save));
  }

  @PostMapping("/{id}/ban")
  public ResponseEntity<Post> ban(@PathVariable String id, @RequestParam(defaultValue = "true") boolean banned) {
    return ResponseEntity.ok(postService.ban(id, banned));
  }
}
