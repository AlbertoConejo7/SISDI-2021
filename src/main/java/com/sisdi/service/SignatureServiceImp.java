package com.sisdi.service;

import com.sisdi.dao.SignatureDao;
import com.sisdi.model.Signature;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignatureServiceImp implements SignatureService{
    @Autowired
    private SignatureDao signatureDao;

    @Override
    public List<Signature> listSignature() {
        return (List<Signature>) signatureDao.findAll();
    }

    @Override
    public Signature addSignature(Signature sign) {
        return signatureDao.save(sign);
    }

    @Override
    public Signature getSignatureByUser(String user) {
        List<Signature> list = this.listSignature();
        Signature sign=null;
        for(Signature s:list){
            if(s.getUSER_ID().equals(user)){
                sign=s;
            }
        }
        return sign;
    }

    @Override
    public boolean userSignature(String user) {
        Signature s=this.getSignatureByUser(user);
        return s != null;
    }
    
}
