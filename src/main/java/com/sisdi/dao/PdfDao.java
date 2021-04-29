package com.sisdi.dao;

import com.sisdi.model.Pdf;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfDao extends CrudRepository<Pdf, Integer>{
    
}
