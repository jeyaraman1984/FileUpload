package com.fileupload.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fileupload.dao.FileDao;
import com.fileupload.models.FileDetails;

public interface FileService {

    public void saveNew(FileDetails file);

    public List<FileDetails> getAll();

    public FileDetails getById(long id);

    public FileDetails getByName(String name);

    public void update(FileDetails file);

    public void deleteById(long id);

    public void deleteByName(String name);

    public FileDetails processUploadedFile(MultipartFile file);

    public void setFileDao(FileDao fileDao);
    
    public FileDao getFileDao();

}
