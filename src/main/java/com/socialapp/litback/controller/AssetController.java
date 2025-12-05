package com.socialapp.litback.controller;

import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    Resource resource = new ClassPathResource("static/assets/" + safeFolder + "/" + safeFile);
    if (!resource.exists()) {
      return ResponseEntity.notFound().build();
    }

    MediaType mediaType =
        safeFile.endsWith(".svg") ? MediaType.valueOf("image/svg+xml") : MediaType.APPLICATION_OCTET_STREAM;
    return ResponseEntity.ok()
        .header(HttpHeaders.CACHE_CONTROL, "max-age=86400, public")
        .contentType(mediaType)
        .body(resource);
  }
}
