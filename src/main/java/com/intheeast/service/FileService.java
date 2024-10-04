package com.intheeast.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.intheeast.dao.FileDao;
import com.intheeast.entity.FileEntity;
import com.intheeast.entity.Post;

@Service
public class FileService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private FileDao fileDao;

    public void storeFile(MultipartFile file, Post post) throws IOException {
        String fileName = file.getOriginalFilename();
        String uploadPath = UPLOAD_DIR + fileName;

        // 파일 저장
        File dest = new File(uploadPath);
        file.transferTo(dest);

        // FileEntity 저장
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFilePath(uploadPath);
        fileEntity.setPost(post);
        fileDao.save(fileEntity);
    }

    public FileEntity findById(Long fileId) {
        return fileDao.findById(fileId);
    }

    public List<FileEntity> findAllByPost(Post post) {
        return fileDao.findAllByPost(post);
    }
}