
package com.sisdi.data;

import com.sisdi.model.OfficeSimple;
import com.sisdi.model.Version;
import com.sisdi.service.VersionServiceImp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AZVal
 */
@Service
public class VersionData {
    
    @Autowired
    private VersionServiceImp versionService;
    
    public Version getVersion(OfficeSimple off) throws ParseException{
        Version v = new Version();
        Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(off.getDateCreate());
        int number = this.versionNumber(off.getOffnumber());
        v.setVersion_date(fecha);
        v.setReason(off.getReason());
        v.setObservations(off.getObservations());
        v.setVersion_id("Version "+number);
        v.setVersion_description(v.getVersion_id()+" de " + off.getOffnumber());
        v.setVersion_number(number);
        v.setOffice_id(off.getOffnumber());
        return v;
    
    }
    
    public int versionNumber(String offNumber){
        List <Version> list = versionService.listVersionByOffice(offNumber);
        return list.size() + 1;
        
    }
    
}
