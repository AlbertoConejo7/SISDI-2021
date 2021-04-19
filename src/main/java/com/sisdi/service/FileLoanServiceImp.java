package com.sisdi.service;

import com.sisdi.dao.FileLoanDao;
import com.sisdi.model.FileLoan;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileLoanServiceImp implements FileLoanService{
    @Autowired
    private FileLoanDao fileLoanDao;
    
    @Override
    public List<FileLoan> listarFileLoans() {
        return (List<FileLoan>) fileLoanDao.findAll();
    }

    @Override
    public FileLoan addFileLoan(FileLoan file) {
        return fileLoanDao.save(file);
    }
    
}
