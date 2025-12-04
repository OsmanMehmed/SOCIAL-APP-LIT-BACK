package com.socialapp.litback;

import com.socialapp.litback.model.UserProfile;
import com.socialapp.litback.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProfileVetTest {

  @Autowired
  private ProfileService profileService;

  @Test
  void vetProfileWithUsernamePersistsBanFlag() {
    String username = "carlitosss";
    profileService.vetProfile(username, true);

    Optional<UserProfile> profile = profileService.getProfile(username);

    assertThat(profile).isPresent();
    assertThat(profile.get().banned()).isTrue();
  }
}
