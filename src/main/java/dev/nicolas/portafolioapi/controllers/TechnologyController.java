package dev.nicolas.portafolioapi.controllers;

import dev.nicolas.portafolioapi.entities.Technology;
import dev.nicolas.portafolioapi.entities.Thumbnail;
import dev.nicolas.portafolioapi.entities.UserProfile;
import dev.nicolas.portafolioapi.response.ResponseModel;
import dev.nicolas.portafolioapi.services.FileService;
import dev.nicolas.portafolioapi.services.TechnologyService;
import dev.nicolas.portafolioapi.services.ThumbnailService;
import dev.nicolas.portafolioapi.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/technology")
public class TechnologyController {
    private final TechnologyService technologyService;
    public final ThumbnailService thumbnailService;
    private final FileService fileService;
    private final UserProfileService userProfileService;

    @Autowired
    public TechnologyController(TechnologyService technologyService,
                                ThumbnailService thumbnailService,
                                FileService fileService,
                                UserProfileService userProfileService) {
        this.technologyService = technologyService;
        this.thumbnailService = thumbnailService;
        this.fileService = fileService;
        this.userProfileService = userProfileService;
    }
    @PostMapping("save")
    public ResponseEntity<ResponseModel<Technology>> save(@RequestParam("name")String name,
                                                          @RequestParam("description")String description,
                                                          @RequestParam("thumbnail")MultipartFile thumbnail,
                                                          @RequestParam("userName")String userName) {
        ResponseModel<Technology> responseModel;
        try {
            Optional<UserProfile> oUser = userProfileService.find(userName);
            if (oUser.isPresent()) {
                UserProfile user = oUser.get();
                String fileName = fileService.uploadFile(thumbnail);
                String contentType = thumbnail.getContentType();
                String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/thumbnail/")
                        .path(fileName)
                        .toUriString();

                Thumbnail thumb = new Thumbnail(fileName, contentType, url);
                Technology technology = new Technology();
                technology.setName(name);
                technology.setDescription(description);
                technology.setThumbnail(thumbnailService.save(thumb));
                technology.setUserProfile(user);

                responseModel = new ResponseModel<>(200, "Created",
                        technologyService.save(technology));
            }else {
                return ResponseEntity.badRequest().body(null);
            }
        }catch (Exception e) {
            responseModel = new ResponseModel<>(400, e.getMessage(), null);
        }
        return ResponseEntity.ok(responseModel);
    }
    @GetMapping("{name}")
    public ResponseEntity<ResponseModel<Technology>> find(@PathVariable("name")String name) {
        ResponseModel<Technology> responseModel;
        try {
            Optional<Technology> oTechnology = technologyService.find(name);
            responseModel = oTechnology.map(technology -> new ResponseModel<>(200, "Found", oTechnology.get()))
                    .orElseGet(() -> new ResponseModel<>(400, "Not Found", null));

        }catch (Exception e) {
            responseModel = new ResponseModel<>(400, e.getMessage(), null);
        }
        return ResponseEntity.ok(responseModel);
    }
    @GetMapping("user/{name}")
    public ResponseEntity<ResponseModel<List<Technology>>> findAll(@PathVariable("name")String name) {
        ResponseModel<List<Technology>> responseModel;
        try {
            Optional<UserProfile> oUser = userProfileService.find(name);
            UserProfile user = oUser.orElseGet(UserProfile::new);
            Optional<List<Technology>> oTechnologies = technologyService.findAll(user);
            List<Technology> technologies = oTechnologies.orElseGet(ArrayList::new);
            responseModel = new ResponseModel<>(200, "Found", technologies);
        }catch (Exception e) {
            responseModel = new ResponseModel<>(400, e.getMessage(), new ArrayList<>());
        }
        return ResponseEntity.ok(responseModel);
    }
}
