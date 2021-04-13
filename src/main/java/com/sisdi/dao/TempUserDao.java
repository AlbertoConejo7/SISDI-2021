package com.sisdi.dao;

import com.sisdi.model.TempUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempUserDao extends CrudRepository<TempUser, Integer> {
    
}