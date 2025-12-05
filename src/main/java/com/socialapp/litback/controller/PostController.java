package com.socialapp.litback.controller;

import com.socialapp.litback.model.Comment;
import com.socialapp.litback.model.Post;
import com.socialapp.litback.model.PostDetails;
import com.socialapp.litback.service.PostService;
import com.socialapp.litback.shared.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

@RestController
@RequestMapping("/api/posts")
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public ResponseEntity<List<Post>> listPosts(
      @RequestHeader(value = "X-User-Id", required = false) String userId) {
    return ResponseEntity.ok(postService.listPosts(userId));
  }

  @GetMapping("/search")
  public ResponseEntity<List<Post>> searchPosts(
      @RequestParam String q,
      @RequestHeader(value = "X-User-Id", required = false) String userId) {
    return ResponseEntity.ok(postService.search(q, userId));
  }

  @GetMapping("/random")
  public ResponseEntity<List<Post>> randomPosts(
      @RequestParam(defaultValue = Constants.DEFAULT_RANDOM_LIMIT) int limit,
      @RequestHeader(value = "X-User-Id", required = false) String userId) {
    return ResponseEntity.ok(postService.randomPosts(limit, userId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPost(
      @PathVariable String id,
      @RequestHeader(value = "X-User-Id", required = false) String userId) {
    return postService.getPost(id, userId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/author/{id}")
  public ResponseEntity<List<Post>> getPostsByAuthor(
      @PathVariable String id,
      @RequestHeader(value = "X-User-Id", required = false) String userId) {
    String authorId = ("me".equalsIgnoreCase(id) || (id == null || id.isBlank()))
        ? userId
        : id;
    if (authorId == null || authorId.isBlank()) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(postService.listPostsByAuthor(authorId, userId));
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<List<Comment>> getComments(@PathVariable String id) {
    return ResponseEntity.ok(postService.getComments(id));
  }

  @PostMapping
  public ResponseEntity<PostDetails> createPost(@RequestBody Post post) {
    return ResponseEntity.ok(postService.create(post));
  }

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<PostDetails> createPostMultipart(
      @RequestParam String title,
      @RequestParam String description,
      @RequestParam String caption,
      @RequestHeader(value = "X-User-Id", required = false) String userId,
      @RequestPart(required = false) List<MultipartFile> images) throws IOException {
    String authorId = (userId == null || userId.isBlank()) ? Constants.DEFAULT_USER_ID : userId;
    List<String> imageUrls = saveImages(images);
    String mainImage = imageUrls.isEmpty() ? null : imageUrls.get(0);
    Post post =
        new Post(
            null,
            title,
            description,
            caption,
            authorId,
            mainImage,
            0,
            0,
            0,
            false,
            false);
    PostDetails created = postService.createWithImages(post, imageUrls);
    return ResponseEntity.ok(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PostDetails> updatePost(@PathVariable String id, @RequestBody Post post) {
    return ResponseEntity.ok(postService.update(new Post(
        id,
        post.title(),
        post.description(),
        post.caption(),
        post.authorId(),
        post.imageUrl(),
        post.likes(),
        post.comments(),
        post.saves(),
        post.banned(),
        post.liked())));
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
  public ResponseEntity<Post> like(
      @PathVariable String id,
      @RequestParam(defaultValue = "true") boolean like,
      @RequestHeader(value = "X-User-Id", required = false) String userId) {
    if (userId == null || userId.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Header X-User-Id requerido");
    }
    return ResponseEntity.ok(postService.like(id, userId, like));
  }

  @PostMapping("/{id}/save")
  public ResponseEntity<Post> save(@PathVariable String id, @RequestParam(defaultValue = "true") boolean save) {
    return ResponseEntity.ok(postService.save(id, save));
  }

  @PostMapping("/{id}/ban")
  public ResponseEntity<Post> ban(@PathVariable String id, @RequestParam(defaultValue = "true") boolean banned) {
    return ResponseEntity.ok(postService.ban(id, banned));
  }

  private List<String> saveImages(List<MultipartFile> files) throws IOException {
    if (files == null || files.isEmpty()) {
      return List.of();
    }
    List<String> urls = new ArrayList<>();
    Path uploadDir = Paths.get("src/main/resources/static/assets/uploads");
    Files.createDirectories(uploadDir);
    for (MultipartFile file : files) {
      if (file.isEmpty()) continue;
      String sanitizedName = file.getOriginalFilename() != null ? file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_") : "image";
      String filename = java.util.UUID.randomUUID() + "-" + sanitizedName;
      Path target = uploadDir.resolve(filename);
      Files.copy(file.getInputStream(), target);
      urls.add("/api/assets/uploads/" + filename);
    }
    return urls;
  }
}
