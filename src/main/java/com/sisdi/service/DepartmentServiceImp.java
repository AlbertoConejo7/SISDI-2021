package com.sisdi.service;

import com.sisdi.dao.DepartmentDao;
import com.sisdi.model.Department;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImp implements DepartmentService{
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public List<Department> listDepartment() {
        return (List<Department>) departmentDao.findAll();
    }
    
}
