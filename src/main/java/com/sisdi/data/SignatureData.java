package com.sisdi.data;

import com.sisdi.model.Signature;
import com.sisdi.service.SignatureServiceImp;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SignatureData {
    @Autowired
    private SignatureServiceImp signatureServiceImp;
    
    public JSONObject verificarCertificado(String cert){
        JSONObject obj = new JSONObject();
        List<Signature> list=signatureServiceImp.listSignature();
        for(Signature s:list){
            if(s.getCERTIFICATE_ID().equals(cert)){
                
                obj.put("Encontrado", true);
               
            }
            
        }
        
        return obj;
    }
}
