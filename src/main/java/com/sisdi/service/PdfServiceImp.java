package com.sisdi.service;

import com.sisdi.dao.PdfDao;
import com.sisdi.model.Pdf;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PdfServiceImp implements PdfService{
    @Autowired
    private PdfDao pdfDao;

    @Override
    public Pdf getPdf(String nameO) {
        List<Pdf> list= this.listPdf();
        Pdf pdf=new Pdf();
        for(Pdf p:list){
            if(p.getOFFICE().equals(nameO)){
                pdf=p;
            }
        }
        return pdf;
    }

    @Override
    public Pdf addPdf(Pdf pdf) {
       return pdfDao.save(pdf);
    }

    @Override
    public List<Pdf> listPdf() {
        return (List<Pdf>) pdfDao.findAll();
    }
    
}
