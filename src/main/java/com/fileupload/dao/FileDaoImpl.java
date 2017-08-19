package com.fileupload.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.fileupload.models.FileDetails;

@Repository
public class FileDaoImpl implements FileDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<FileDetails> getAll() {
    String hql = "FROM File as files ORDER BY files.fileId";
    return (List<FileDetails>) entityManager.createQuery(hql).getResultList();
    }

    public void save(FileDetails file) {
        entityManager.persist(file);
    }

    public FileDetails get(long id) {
        FileDetails file = entityManager.find(FileDetails.class, id);
        return file;
    }

    public void update(FileDetails file) {
        System.out.println(file.getFileName());

        entityManager.merge(file);
    }

    public void delete(long id) {
        FileDetails file = get(id);
        if (file != null) {
            entityManager.remove(file);
        }
    }

}
