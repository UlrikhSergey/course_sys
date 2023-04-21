package com.course_sys.service;

import com.course_sys.entity.FileEntity;
import com.course_sys.exception.CourseNotFoundException;
import com.course_sys.repository.FileRepository;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class FileServiceTest {
    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;
    private FileEntity fileEntity1;

    @BeforeEach
    public void setUp(){
        fileEntity1 = FileEntity.builder()
                .id("Backend")
                .build();

    }
    @Test
    void save() throws IOException {
        Path path = Paths.get("com/course_sys/resources/test1.txt");
        String name = "test1.txt";
        String originalFileName = "test1.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        fileService.save(result,fileEntity1);
        assertThat(fileEntity1.getSize()).isNotNull();
        assertThat(fileEntity1.getContentType()).isNotNull();
        assertThat(fileEntity1.getData()).isNotNull();
        assertEquals(fileEntity1.getData(), result.getBytes());
    }


    @Test
    void getFile() {
        given(fileRepository.findById("Backend")).willReturn(Optional.of(fileEntity1));
        FileEntity savedFileEntity = fileService.getFile(fileEntity1.getId()).get();
        assertThat(savedFileEntity).isNotNull();
    }
    @Test
    void getFileWhenIdNotFound() {
        given(fileRepository.findById("HR")).willReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () ->{
            fileService.getFile("HR");
        });
    }
}