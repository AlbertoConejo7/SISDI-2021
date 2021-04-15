package com.sisdi.service;

import com.sisdi.model.Transfer;
import java.util.List;

public interface TransferService {
    
    List<Transfer> listTrasfers();
    
    Transfer addTransfer(Transfer transfer);
    
    List<Transfer> listTransfersState(int state); 
    
    Transfer searchTransfer(int INDX);
}
