package com.sisdi.service;

import com.sisdi.dao.UserDao;
import com.sisdi.model.UserEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
    @Autowired 
    private UserDao userDao;
    @Override
    public List<UserEntity> listUsers() {
        return (List<UserEntity>) userDao.findAll();
    }
    
}
