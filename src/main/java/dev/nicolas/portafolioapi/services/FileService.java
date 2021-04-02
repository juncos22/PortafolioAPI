package dev.nicolas.portafolioapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    private final String uploadLocation;
    private final Path uploadPath;
    @Autowired
    public FileService(@Value("${file.storage.location}") String uploadLocation) {
        this.uploadLocation = uploadLocation;
        this.uploadPath = Paths.get(uploadLocation).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create the directory", e);
        }
    }

    public String uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename() != null ? StringUtils.cleanPath(file.getOriginalFilename()) : "";
        Path filePath = Paths.get(String.format("%s\\%s", uploadPath, fileName));
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue copying the file", e);
        }

        return fileName;
    }
    public Resource downloadFile(String fileName) {
        Path filePath = Paths.get(uploadLocation).toAbsolutePath().resolve(fileName);
        System.out.println(filePath.toUri());
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            System.out.println("Resource Exists: "+resource.exists());
            System.out.println("Resource Is Readable: "+resource.isReadable());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue reading the file", e);
        }
        if (resource.exists() && resource.isReadable()) {
            return resource;
        }else {
            throw new RuntimeException("The file doesn't exists or isn't readable");
        }
    }
}
