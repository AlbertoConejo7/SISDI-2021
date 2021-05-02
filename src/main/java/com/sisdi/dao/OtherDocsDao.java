package com.sisdi.dao;

import com.sisdi.model.OtherDocs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherDocsDao extends CrudRepository<OtherDocs, Integer>{
    
}
