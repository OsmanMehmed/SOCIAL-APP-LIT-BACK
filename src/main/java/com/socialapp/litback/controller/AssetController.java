package com.socialapp.litback.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assets")
public class AssetController {

  @GetMapping("/{folder}/{file}")
  public ResponseEntity<Resource> getAsset(
      @PathVariable String folder,
      @PathVariable String file) throws IOException {
    String safeFolder = folder.replaceAll("[^a-zA-Z0-9_-]", "");
    String safeFile = file.replaceAll("[^a-zA-Z0-9_.-]", "");

    Path uploadPath = Paths.get("uploads").resolve(safeFolder).resolve(safeFile);
    Resource resource = new org.springframework.core.io.FileSystemResource(
        java.util.Objects.requireNonNull(uploadPath));

    if (!resource.exists()) {
      resource = new ClassPathResource("static/assets/" + safeFolder + "/" + safeFile);
    }

    if (!resource.exists()) {
      return ResponseEntity.notFound().build();
    }

    MediaType mediaType = MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM);
    return ResponseEntity.ok()
        .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
        .contentType(java.util.Objects.requireNonNull(mediaType))
        .body(resource);
  }
}
