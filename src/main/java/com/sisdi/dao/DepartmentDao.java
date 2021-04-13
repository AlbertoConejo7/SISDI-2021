package com.sisdi.dao;

import com.sisdi.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Integer>{
    
}
