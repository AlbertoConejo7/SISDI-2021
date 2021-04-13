package com.sisdi.dao;

import com.sisdi.model.State;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDao extends CrudRepository<State, Integer>{
    
}
