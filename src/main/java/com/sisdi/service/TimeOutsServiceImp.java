package com.sisdi.service;

import com.sisdi.dao.TimeOutsDao;
import com.sisdi.model.TimeOuts;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeOutsServiceImp implements TimeOutsService{
    @Autowired
    private TimeOutsDao TimeOutsDao;

    @Override
    public List<TimeOuts> listTimeOuts() {
        return (List<TimeOuts>) TimeOutsDao.findAll();
    }
     @Override          
   public  TimeOuts searchTimeOut(int id){
     List<TimeOuts> list= this.listTimeOuts();
         TimeOuts aux =null;
         for(TimeOuts o:list){
             if(o.getID()==id){
                 aux=o;
             }
         }
         return aux;
    }
    
}
