package com.sisdi.service;

import com.sisdi.dao.OtherDocsDao;
import com.sisdi.model.OtherDocs;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtherDocsServiceImp implements OtherDocsService{
    @Autowired
    private OtherDocsDao otherDocsDao;

    @Override
    public List<OtherDocs> getOtherDocs(String nameO) {
        List<OtherDocs> list= this.listOtherDocs();
        List<OtherDocs> list2= new ArrayList();
        for(OtherDocs p:list){
            if(p.getOFFICE().equals(nameO)){
                list2.add(p);
            }
        }
        return list2;
    }

    @Override
    public OtherDocs addOtherDocs(OtherDocs od) {
       return otherDocsDao.save(od);
    }

    @Override
    public List<OtherDocs> listOtherDocs() {
        return (List<OtherDocs>) otherDocsDao.findAll();
    }

    @Override
    public List<OtherDocs> addOtherDocs(List<OtherDocs> list ) {
        for(OtherDocs p:list){
          this.addOtherDocs(p);
        }
        return list;
    } 

    @Override
    public OtherDocs getOtherDocs(String office, String name) {
        List<OtherDocs> list= this.getOtherDocs(office);
        OtherDocs o=null;
        for(OtherDocs p:list){
            if(p.getNAME_DOC().equals(name)){
                o=p;
            }
        }
        return o;
    }
}
