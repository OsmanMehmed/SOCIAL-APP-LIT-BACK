package com.socialapp.litback;

import com.socialapp.litback.model.UserProfile;
import com.socialapp.litback.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileAvatarUploadTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileService profileService;

    @Test
    void uploadAvatar() throws Exception {
        UserProfile user = new UserProfile("test-user-id", "TestUser", "Subtitle", false, false, null, null, false,
                true);
        profileService.createProfile(user);

        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "some-image".getBytes());
        mockMvc.perform(multipart("/api/profiles/test-user-id/avatar")
                .file(file)
                .header("X-User-Id", "test-user-id"))
                .andExpect(status().isOk());
    }
}
