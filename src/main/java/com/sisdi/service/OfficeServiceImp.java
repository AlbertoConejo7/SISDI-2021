package com.sisdi.service;

import com.sisdi.dao.OfficeDao;
import com.sisdi.data.UserData;
import com.sisdi.model.Office;
import com.sisdi.model.Usuario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficeServiceImp implements OfficeService {

    @Autowired
    private OfficeDao officeDao;
    
    @Autowired
    private UserData userData;

    @Override
    public List<Office> listarOficios() {
        return (List<Office>) officeDao.findAll();
    }

    @Override
    public Office addOffice(Office office) {
        return officeDao.save(office);
    }

    @Override
    public Office searchOffice(String offnumber) {
        List<Office> list = this.listarOficios();
        Office aux = null;
        for (Office o : list) {
            if (o.getOFFNUMBER().equals(offnumber)) {
                aux = o;
            }
        }
        return aux;
    }

    @Override
    public List<Office> listOfficeByEmisor(String emisor) {
        List<Office> list = this.listarOficios();
        List<Office> aux = new ArrayList();
        for (Office o : list) {
            if (o.getUSER_ID().equals(emisor)) {
                aux.add(o);
            }
        }

        return aux;
    }

    @Override
    public List<Office> listOfficeByReceptor(String receptor) {
        List<Office> list = this.listarOficios();
        List<Office> aux = new ArrayList();
        for (Office o : list) {
            if (o.getRECEIVER_ID().equals(receptor) && o.getSTATE() != 3) {
                aux.add(o);
            }
        }

        return aux;
    }

    @Override
    public List<Office> listOfficeByUser(String user) {
        List<Office> aux = new ArrayList();
        List<Office> emisor = this.listOfficeByEmisor(user);
        List<Office> receptor = this.listOfficeByReceptor(user);
        aux.addAll(emisor);
        aux.addAll(receptor);
        return aux;
    }

    @Override
    public List<Office> listByName(List<Office> list, String name) {
        List<Office> aux = new ArrayList();
        String nameAux1 = name.toLowerCase();
        for (Office o : list) {
            String nameAux2 = o.getOFFNUMBER().toLowerCase();
            if (nameAux2.contains(nameAux1)) {
                aux.add(o);
            }
        }
        return aux;
    }

    @Override
    public List<Office> listByDate(List<Office> list, Date date) {
        List<Office> aux = new ArrayList();
        for (Office o : list) {
            if (date.equals(o.getINCORDATE())) {
                aux.add(o);
            }
        }
        return aux;
    }

    @Override
    public List<Office> listByReason(List<Office> list, String reason) {
        List<Office> aux = new ArrayList();
        String reasonAux1 = reason.toLowerCase();
        for (Office o : list) {
            String reasonAux2 = o.getREASON().toLowerCase();
            if (reasonAux2.contains(reasonAux1)) {
                aux.add(o);
            }
        }
        return aux;
    }

    @Override
    public List<Office> listarOficiosExp(int indx) {
        List<Office> list = this.listarOficios();
        List<Office> aux = new ArrayList();
        for (Office o : list) {
            //  o.getEXPEDIENTE().toLowerCase();
            if (indx != 0) {
                if (o.getEXPEDIENTE() == indx) {
                    aux.add(o);
                }
            }
        }
        return aux;
    }

    @Override
    public void deleteOfficesExp(int indx) {
        List<Office> list = this.listarOficios();
        List<Office> aux = new ArrayList();
        for (Office o : list) {
            //  o.getEXPEDIENTE().toLowerCase();
            if (indx != 0) {
                if (o.getEXPEDIENTE() == indx) {
                    officeDao.delete(o);
                }
            }
        }

    }
    
    @Override
    public List<Office> listOfficeByYear(int y) {
        List<Office> list = this.listarOficios();
        List<Office> aux = new ArrayList();
        for (Office e : list) {
            String eDate = new SimpleDateFormat("yyyy").format(e.getINCORDATE());
            int year = Integer.parseInt(eDate);
            if (year == y) {
                aux.add(e);
            }
        }
        return aux;
    }

    @Override
    public List<Office> listOfficeByDepartment(String departamento) {
        List<Office> list = this.listarOficios();
        List<Office> aux = new ArrayList();
        for (Office o : list) {
            Usuario user = userData.getUser(o.getUSER_ID());                    
            if (user.getDepartment().getName().equals(departamento) ) {
                aux.add(o);
            }
        }

        return aux;
    }

    @Override
    public List<Office> listOfficeByState3() {
        List<Office> list = this.listarOficios();
        List<Office> aux = new ArrayList();
        for (Office o : list) {
            if (o.getSTATE()==3) {
                aux.add(o);
            }
        }

        return aux;
    }
}
