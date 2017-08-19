package com.fileupload.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fileupload.dao.FileDao;
import com.fileupload.models.FileDetails;
import com.fileupload.models.Customer;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    @Override
    public FileDao getFileDao() {
        return fileDao;
    }

    @Override
    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    @Transactional
    public void saveNew(FileDetails file) {
        fileDao.save(file);
    }

    @Override
    public List<FileDetails> getAll() {
        return fileDao.getAll();
    }

    @Override
    public FileDetails getById(long id) {
        return fileDao.get(id);
    }

    @Override
    public FileDetails getByName(String name) {
        List<FileDetails> database = getAll();

        return database.stream().filter(t -> t.getFileName().equals(name)).findAny().orElse(null);
    }

    @Override
    @Transactional
    public void update(FileDetails file) {
        fileDao.update(file);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        fileDao.delete(id);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        List<FileDetails> database = getAll();

        FileDetails fileToDelete = database.stream().filter(t -> t.getFileName().equals(name)).findAny().orElse(null);
        if (fileToDelete != null) {
            fileDao.delete(fileToDelete.getFileId());
        }

    }

    @Override
    @Transactional
    public FileDetails processUploadedFile(MultipartFile file) {
        Properties properties = new Properties();
        FileDetails uploadedFile = null;

        try (InputStream input = file.getInputStream()) 
        {
            properties.load(input);

            String firstName = properties.getProperty("firstName");
            String lastName = properties.getProperty("lastName");
            String address = properties.getProperty("address");
            Customer customer = new Customer(firstName, lastName, address);
            uploadedFile = new FileDetails(file.getOriginalFilename(), new Date(), customer);
            

            saveNew(uploadedFile);
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return uploadedFile;

    }

}
