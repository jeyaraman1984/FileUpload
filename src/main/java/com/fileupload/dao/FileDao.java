package com.fileupload.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.fileupload.models.FileDetails;

public interface FileDao {

    public void save(FileDetails file);

    public FileDetails get(long id);
    
    public void update(FileDetails file);
    
    public void delete(long id);

    public List<FileDetails> getAll();

    public EntityManager getEntityManager();

    public void setEntityManager(EntityManager entityManager);

}
