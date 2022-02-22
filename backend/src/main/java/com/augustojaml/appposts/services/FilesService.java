package com.augustojaml.appposts.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import com.augustojaml.appposts.exceptions.services.ServiceUploadFileException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesService {

  @Value("${path.files}")
  private String pathFiles;

  public String saveFile(MultipartFile file, String folder) {
    return (file != null) ? this.uploadFile(file, folder) : null;
  }

  public void removeFile(String file, String folder) {
    if (file == null) {
      return;
    }
    this.deleteFile(file, folder);
  }

  private String uploadFile(MultipartFile file, String folder) {
    try {
      File directory = new File(String.valueOf(this.pathFiles + "/" + folder));

      if (!directory.exists()) {
        directory.mkdirs();
      }

      String extensionFile = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1);
      String newImageName = folder + "-" + new Date().getTime() + "." + extensionFile;
      Path path = Paths.get(this.pathFiles + "/" + folder, newImageName);
      Files.copy(file.getInputStream(), path);
      return path.toFile().getName();
    } catch (IOException ex) {
      throw new ServiceUploadFileException("Upload file failed :" + ex.getMessage());
    }
  }

  private void deleteFile(String file, String folder) {
    try {
      Path fileToDeletePath = Paths.get(this.pathFiles + "/" + folder, file);
      if (Files.exists(fileToDeletePath)) {
        Files.delete(fileToDeletePath);
      }
    } catch (IOException e) {
      return;
    }
  }
}