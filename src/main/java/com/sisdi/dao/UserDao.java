package com.sisdi.dao;

import com.sisdi.model.UserEntity;
import org.springframework.data.repository.CrudRepository;


public interface UserDao extends CrudRepository<UserEntity, Integer>{
    
}
