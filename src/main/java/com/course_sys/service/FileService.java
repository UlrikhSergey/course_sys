package com.course_sys.service;

import com.course_sys.entity.FileEntity;
import com.course_sys.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity save(MultipartFile file, FileEntity fileEntity) throws IOException {
        fileEntity.setContentType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setSize(file.getSize());

        return fileRepository.save(fileEntity);
    }

    public Optional<FileEntity> getFile(String id) {
        FileEntity fileEntity = null;
        Optional<FileEntity> optional = fileRepository.findById(id);
        if (optional.isPresent()) {
            return optional;
        }
        else {
            throw new NoSuchElementException("Course with id - " + id + " not found");
        }
    }

}