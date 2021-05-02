package com.sisdi.data;

import com.sisdi.model.OtherDocs;
import java.io.IOException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class DocsData {
    public OtherDocs getOtherDocs(MultipartFile file, String office) throws IOException{
        OtherDocs o=new OtherDocs();
        log.info("Filename: "+file.getOriginalFilename());
        String[] fileFrags = file.getOriginalFilename().split("\\.");
        String extension = fileFrags[fileFrags.length-1];
        log.info("Extension: "+extension);
        byte[] bytes=this.convertFile(file);
        o.setOFFICE(office);
        o.setNAME_DOC(file.getOriginalFilename());
        o.setTYPE_DOC(extension);
        o.setURL(bytes);
        log.info("Documento: "+o.toString());
        return o;
    }
    public byte[] convertFile(MultipartFile file) throws IOException{
        byte[] bytes = null;
        try{
            bytes = file.getBytes();
        }catch(IOException e){
            log.info(e.toString());
        }
        log.info("Bytes "+bytes);
        return bytes;
    }
}
