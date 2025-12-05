package com.socialapp.litback;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.socialapp.litback.model.Post;
import com.socialapp.litback.model.PostDetails;
import com.socialapp.litback.service.PostService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostUploadTest {

  @Autowired private PostService postService;

  @Test
  void createWithImagesSucceeds() {
    Post post = new Post(null, "t", "d", "c", "user-1", null, 0, 0, 0, false, false);
    PostDetails created = postService.createWithImages(post, List.of());
    assertNotNull(created.id());
  }
}
