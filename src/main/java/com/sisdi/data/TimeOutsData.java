/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.data;

import com.sisdi.model.TimeOuts;
import com.sisdi.service.TimeOutsServiceImp;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Data Department
@Service
public class TimeOutsData {
    @Autowired
    private TimeOutsServiceImp timeOutsServiceImp;
    
    private  HashMap<Integer, TimeOuts> listTimeOuts;
     
     public void init(){
        listTimeOuts = new HashMap();
        crearLista();
    }
     
    public void crearLista(){ 
        List<TimeOuts> tempTimeOuts=timeOutsServiceImp.listTimeOuts();
        for(TimeOuts tD:tempTimeOuts){
              listTimeOuts.put(tD.getID(), tD);
          }
    }
    public TimeOuts getTimeOut(int i){
        return this.listTimeOuts.get(i);
    }
}
