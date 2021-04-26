package com.sisdi.dao;

import com.sisdi.model.Signature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureDao extends CrudRepository<Signature, Integer>{
    
}
