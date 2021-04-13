/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sisdi.data;

import com.sisdi.model.TempUser;
import com.sisdi.service.TempUserServiceImp;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempUserData {
    @Autowired
    private TempUserServiceImp tempUserServiceImp;
    
    private  HashMap<String, TempUser> listTempUser;
    
    public void init(){
        this.listTempUser=new HashMap();
        this.crearLista();
    }

    public void crearLista(){
        List<TempUser> tempUsers=tempUserServiceImp.listarTempUser();
          for(TempUser tU:tempUsers){
              listTempUser.put(tU.getEmail(),tU);
          }
    }
    
    public TempUser getTempUser(String email) {
        return listTempUser.get(email);
    }
}
