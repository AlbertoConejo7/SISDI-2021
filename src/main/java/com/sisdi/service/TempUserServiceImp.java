package com.sisdi.service;

import com.sisdi.dao.TempUserDao;
import com.sisdi.model.TempUser;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempUserServiceImp implements TempUserService{
    @Autowired
    private TempUserDao tempUserDao;
  
    @Override
    public List<TempUser> listarTempUser() {
        return  (List<TempUser>) tempUserDao.findAll();
    }

    @Override
    public TempUser addTempUser(TempUser u) {
        return tempUserDao.save(u);
    }
    
}
