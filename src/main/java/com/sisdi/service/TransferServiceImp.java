package com.sisdi.service;

import com.sisdi.dao.TransferDao;
import com.sisdi.model.Transfer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImp implements TransferService{
    @Autowired
    private TransferDao transferDao;

    @Override
    public List<Transfer> listTransfers() {
        return (List<Transfer>) transferDao.findAll();
    }

    @Override
    public Transfer addTransfer(Transfer transfer) {
        return transferDao.save(transfer);
    }

    @Override
    public List<Transfer> listTransfersState(int state) {
        List<Transfer> transfers=this.listTransfers();
        List<Transfer> list= new ArrayList();
        for(Transfer t: transfers){
            if(t.getSTATE()== state){
                list.add(t);
            }
        }
        return list;
    }
    
    @Override
    public Transfer searchTransfer(int INDX){
         List<Transfer> transfers=this.listTransfers();
         Transfer tr= new Transfer();
         for(Transfer t: transfers){
            if(t.getINDX()== INDX){
                tr=t;
            }
        }
         return tr;
    
    }
}
