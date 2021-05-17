package com.sisdi.service;

import com.sisdi.dao.FileLoanDao;
import com.sisdi.model.Expediente;
import com.sisdi.model.FileLoan;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileLoanServiceImp implements FileLoanService{
    @Autowired
    private FileLoanDao fileLoanDao;
    
    @Autowired
    private ExpedienteServiceImp expedienteServiceImp;
    
    
    private Date fecha = new Date();
    
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
    
    @Override
    public List<FileLoan> listarFileLoanByState(int s) {
        List<FileLoan> list = this.listarFileLoans();
        List<FileLoan> aux = new ArrayList();
        for (FileLoan e : list) {
            if (e.getSTATE()==s) {
                aux.add(e);
            }
        }
        return aux;
    }
    
    @Override
    public void reviewFile(){
        List<FileLoan> list = this.listarFileLoanByState(1);
        for (FileLoan e : list) {
            if (e.getDATE_RETURN().equals(fecha)) {
                e.setSTATE(2);
                Expediente ex = expedienteServiceImp.getExpediente(e.getFILENAME());
                ex.setSTATE(1);
                ex.setRECEIVER_ID("archivocentral@sanpablo.go.cr");
                this.addFileLoan(e);
                expedienteServiceImp.addExpediente(ex);
            }
        }
    }
    
}