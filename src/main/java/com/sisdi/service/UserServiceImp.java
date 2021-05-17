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

    @Override
    public UserEntity addUser(UserEntity u) {
        return userDao.save(u);
    }

    @Override
    public UserEntity getUser(String u) {
        List<UserEntity> list=this.listUsers();
        UserEntity us=null;
        for(UserEntity user:list){
            if(user.getTempuser().equals(u)){
                us=user;
            }
        }
        return us;
    }

    @Override
    public void deleteUser(UserEntity u) {
        userDao.delete(u);
    }
    
}
