package com.sisdi.service;

import com.sisdi.model.FileLoan;
import java.util.List;


public interface FileLoanService {
     List<FileLoan> listarFileLoans();

    FileLoan addFileLoan(FileLoan file);
    
}
