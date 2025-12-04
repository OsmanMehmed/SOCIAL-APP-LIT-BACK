package com.socialapp.litback;

import com.socialapp.litback.model.UserProfile;
import com.socialapp.litback.service.FriendService;
import com.socialapp.litback.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class FriendshipFlowTest {

  @Autowired
  private FriendService friendService;

  @Autowired
  private ProfileService profileService;

  @BeforeEach
  void setupFriendship() {
    
    friendService.disconnect("user-2", "user-1");
  }

  @Test
  void connectMarksFriendInProfileResponse() {
    friendService.connect("user-2", "user-1");

    Optional<UserProfile> profile = profileService.getProfile("user-1", "user-2");

    assertThat(profile).isPresent();
    assertThat(profile.get().friend()).isTrue();
  }
}
