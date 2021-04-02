package dev.nicolas.portafolioapi.controllers;

import dev.nicolas.portafolioapi.entities.Project;
import dev.nicolas.portafolioapi.entities.Technology;
import dev.nicolas.portafolioapi.entities.Thumbnail;
import dev.nicolas.portafolioapi.entities.UserProfile;
import dev.nicolas.portafolioapi.response.ResponseModel;
import dev.nicolas.portafolioapi.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    private final ProjectService projectService;
    private final ThumbnailService thumbnailService;
    private final UserProfileService userProfileService;
    private final TechnologyService technologyService;
    private final FileService fileService;

    @Autowired
    public ProjectController(ProjectService projectService,
                             ThumbnailService thumbnailService,
                             UserProfileService userProfileService,
                             TechnologyService technologyService,
                             FileService fileService) {
        this.projectService = projectService;
        this.thumbnailService = thumbnailService;
        this.userProfileService = userProfileService;
        this.technologyService = technologyService;
        this.fileService = fileService;
    }
    @PostMapping("save")
    public ResponseEntity<ResponseModel<Project>> save(@RequestParam("title")String title,
                                                       @RequestParam("description")String description,
                                                       @RequestParam("technologyName")String technologyName,
                                                       @RequestParam("userName")String userName,
                                                       @RequestParam("thumbnails")List<MultipartFile> thumbnails) {
        ResponseModel<Project> responseModel;
        try {
            Optional<UserProfile> oUser = userProfileService.find(userName);
            UserProfile user = oUser.orElseGet(UserProfile::new);

            Optional<Technology> oTechnology = technologyService.find(technologyName);
            Technology technology = oTechnology.orElseGet(Technology::new);

            List<Thumbnail> thumbnailList = new ArrayList<>();
            thumbnails.forEach(thumbnail -> {
                String fileName = fileService.uploadFile(thumbnail);
                String contentType = thumbnail.getContentType();
                String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/thumbnail/")
                        .path(fileName)
                        .toUriString();

                Thumbnail thumb = new Thumbnail(fileName, contentType, url);
                thumbnailList.add(thumbnailService.save(thumb));
            });
            Project project = new Project();
            project.setTitle(title);
            project.setDescription(description);
            project.setTechnology(technology);
            project.setUserProfile(user);
            project.setThumbnails(thumbnailList);

            responseModel = new ResponseModel<>(200, "Created", projectService.save(project));
        }catch (Exception e) {
            responseModel = new ResponseModel<>(400, e.getMessage(), null);
        }
        return ResponseEntity.ok(responseModel);
    }
    @GetMapping("user/{name}")
    public ResponseEntity<ResponseModel<List<Project>>> findAll(@PathVariable("name")String name) {
        ResponseModel<List<Project>> responseModel;
        try {
            Optional<UserProfile> oUser = userProfileService.find(name);
            UserProfile user = oUser.orElseGet(UserProfile::new);

            List<Project> projects = projectService.findAll(user);
            responseModel = new ResponseModel<>(200, "Found", projects);
        }catch (Exception e) {
            responseModel = new ResponseModel<>(400, e.getMessage(), new ArrayList<>());
        }
        return ResponseEntity.ok(responseModel);
    }
}
