package com.sisdi.service;

import com.sisdi.model.Signature;
import java.util.List;


public interface SignatureService {
    List<Signature> listSignature();
    Signature addSignature(Signature sign);
}
