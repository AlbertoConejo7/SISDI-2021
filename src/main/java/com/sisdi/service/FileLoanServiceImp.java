package com.sisdi.service;

import com.sisdi.dao.FileLoanDao;
import com.sisdi.model.FileLoan;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    
    public FileLoan searchFileLoan(int idRequest){
         List<FileLoan> list = this.listarFileLoans();
         FileLoan fl = null;
         for(FileLoan f : list){
            if(f.getFILE_ID()==idRequest){
                fl = f;
            }
        }
        return fl;
    }
    
    @Override
    public List<FileLoan> listarFileRequests() {
        List<FileLoan> list = this.listarFileLoans();
        List<FileLoan> requests = new ArrayList();
        for(FileLoan f : list){
            if(f.getSTATE()==0){
                requests.add(f);
            }
        }
        return requests;
    }


    @Override
    public FileLoan addFileLoan(FileLoan file) {
        return fileLoanDao.save(file);
    }

    @Override
    public List<FileLoan> listFileLoanByDepartment(String dep) {
        List<FileLoan> list = this.listarFileLoans();
        List<FileLoan> aux = new ArrayList();
        for (FileLoan e : list) {
            if (e.getFILENAME().contains(dep)) {
                aux.add(e);
            }
        }
        return aux;
    }
    
    @Override
    public List<FileLoan> listFileLoanByYear(int y) {
        List<FileLoan> list = this.listarFileLoans();
        List<FileLoan> aux = new ArrayList();
        for (FileLoan e : list) {
            String eDate = new SimpleDateFormat("yyyy").format(e.getDATE_CREATE());
            int year = Integer.parseInt(eDate);
            if (year == y) {
                aux.add(e);
            }
        }
        return aux;
    }
    
}