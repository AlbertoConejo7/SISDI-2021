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
    
}
