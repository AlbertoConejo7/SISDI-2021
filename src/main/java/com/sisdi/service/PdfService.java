package com.sisdi.service;

import com.sisdi.model.Pdf;
import java.util.List;


public interface PdfService {
    List<Pdf> listPdf();
    Pdf getPdf(String nameO);
    Pdf addPdf(Pdf pdf);
}
