package com.sisdi.service;

import com.sisdi.model.FileLoan;
import java.util.List;


public interface FileLoanService {
     List<FileLoan> listarFileLoans();

    FileLoan addFileLoan(FileLoan file);
    List<FileLoan> listarFileRequests();
    
    List<FileLoan> listFileLoanByDepartment(String dep);
    List<FileLoan> listFileLoanByYear(int y);
    
    public List<FileLoan> listarFileLoanByState(int s);
    
    void reviewFile();
}
