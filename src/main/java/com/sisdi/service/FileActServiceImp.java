/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.service;

import com.sisdi.dao.FileActDao;
import com.sisdi.model.FileAct;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileActServiceImp implements FileActService{
    @Autowired
    private FileActDao fileActDao;
    
    @Override
    public List<FileAct> listarFileActs() {
        return (List<FileAct>) fileActDao.findAll();
    }

    @Override
    public FileAct addFileAct(FileAct file) {
        return fileActDao.save(file);
    }
    
}
