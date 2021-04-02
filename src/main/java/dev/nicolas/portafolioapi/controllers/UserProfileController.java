package dev.nicolas.portafolioapi.controllers;

import dev.nicolas.portafolioapi.entities.Thumbnail;
import dev.nicolas.portafolioapi.entities.UserProfile;
import dev.nicolas.portafolioapi.response.ResponseModel;
import dev.nicolas.portafolioapi.services.FileService;
import dev.nicolas.portafolioapi.services.ThumbnailService;
import dev.nicolas.portafolioapi.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final FileService fileService;
    private final ThumbnailService thumbnailService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService,
                                 FileService fileService,
                                 ThumbnailService thumbnailService) {
        this.userProfileService = userProfileService;
        this.fileService = fileService;
        this.thumbnailService = thumbnailService;
    }

    @PostMapping("save")
    public ResponseEntity<ResponseModel<UserProfile>> save(@RequestParam("name") String name,
                                              @RequestParam("email") String email,
                                              @RequestParam("bio") String bio,
                                              @RequestParam("thumbnail")MultipartFile thumbnail) {

        ResponseModel<UserProfile> responseModel;
        try {
            String fileName = fileService.uploadFile(thumbnail);
            String contentType = thumbnail.getContentType();
            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/thumbnail/")
                    .path(fileName)
                    .toUriString();

            Thumbnail thumb = new Thumbnail(fileName, contentType, url);
            UserProfile userProfile = new UserProfile();
            userProfile.setName(name);
            userProfile.setEmail(email);
            userProfile.setBio(bio);
            userProfile.setThumbnail(thumbnailService.save(thumb));
            responseModel = new ResponseModel<>(200, "Created", userProfileService.save(userProfile));
        }catch (Exception e) {
            responseModel = new ResponseModel<>(400, e.getMessage(), null);
        }
        return ResponseEntity.ok(responseModel);
    }
    @GetMapping("{name}")
    public ResponseEntity<ResponseModel<UserProfile>> get(@PathVariable String name) {
        ResponseModel<UserProfile> responseModel;
        try {
            Optional<UserProfile> oUser = userProfileService.find(name);
            responseModel = oUser.map(userProfile -> new ResponseModel<>(200, "Found", userProfile))
                    .orElseGet(() -> new ResponseModel<>(400, "Not Found", null));
        }catch (Exception e) {
            responseModel = new ResponseModel<>(400, e.getMessage(), null);
        }
        return ResponseEntity.ok(responseModel);
    }

}
