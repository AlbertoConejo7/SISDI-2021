package com.sisdi.service;

import com.sisdi.model.OtherDocs;
import java.util.List;


public interface OtherDocsService {
    List<OtherDocs> listOtherDocs();
    List<OtherDocs> getOtherDocs(String nameO);
    List<OtherDocs> addOtherDocs(List<OtherDocs> list);
    OtherDocs addOtherDocs(OtherDocs od);
    OtherDocs getOtherDocs(String office, String name);
}
