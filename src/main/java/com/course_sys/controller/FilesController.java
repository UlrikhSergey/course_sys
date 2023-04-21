package com.course_sys.controller;

import com.course_sys.entity.FileEntity;
import com.course_sys.repository.FileRepository;
import com.course_sys.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("files")
public class FilesController {

    private final FileService fileService;
    private final FileRepository fileRepository;

    @Autowired
    public FilesController(FileService fileService, FileRepository fileRepository) {
        this.fileService = fileService;
        this.fileRepository = fileRepository;
    }

    @PreAuthorize("hasAuthority('TEACHER')")
    @PostMapping("/{id}")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @PathVariable String id) {
        try {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setId(id);
            fileService.save(file,fileEntity);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s, name=%s", file.getOriginalFilename(), fileEntity.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }
    @PreAuthorize("hasAuthority('TEACHER')")
    @GetMapping
    public List<FileEntity> getAllFiles (){
        return (List<FileEntity>) fileRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<FileEntity> fileEntityOptional = fileService.getFile(id);

        if (!fileEntityOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        FileEntity fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }
}
