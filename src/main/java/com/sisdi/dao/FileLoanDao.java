package com.sisdi.dao;

import com.sisdi.model.FileLoan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileLoanDao extends CrudRepository<FileLoan, Integer>{
    
}
