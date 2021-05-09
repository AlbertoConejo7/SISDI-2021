package com.sisdi.data;

import com.sisdi.model.UserEntity;
import com.sisdi.model.Usuario;
import com.sisdi.service.UserServiceImp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Data User
@Service
public final class UserData {

    private HashMap<String, Usuario> listUser;
    
    @Autowired
    private UserServiceImp uServ;
    @Autowired
    private TempUserData tUServ;

    @Autowired
    private DepartmentData depServ;

    public void init() {
        this.listUser = new HashMap();
        tUServ.init();
        depServ.init();
        crearLista();
    }

    public void crearLista() {
        List<UserEntity> users = uServ.listUsers();
        for (UserEntity u : users) {
            listUser.put(u.getTempuser(), new Usuario(this.tUServ.getTempUser(u.getTempuser()),
                    u.getPassword(), this.depServ.getDepartment(u.getDepartment()), is(u.getStatus()), is(u.getIsboss())));
        }
    }

    public Usuario login(String email, String pass) throws Exception {
        if (listUser.get(email) != null && listUser.get(email).getPassword().equals(pass)) {
            return listUser.get(email);
        } else {
            throw new Exception("Usuario no existe");
        }
    }

    public Usuario getUser(String email) {
        return listUser.get(email);
    }

    public boolean getUserRol(String email) {
        return listUser.get(email).getIsBoss();
    }

    public Usuario getUserByName(String name) {
        Usuario aux = null;
        for (Usuario u : listUser.values()) {
            if (u.getTempUser().getName().equals(name)) {
                aux = u;
            }
        }
        return aux;
    }

    public HashMap<String, Usuario> getUsuarios() {
        return listUser;
    }

    public boolean is(int i) {
        return i == 1;
    }

    public List<Usuario> listUsers() {
        List<Usuario> aux = new ArrayList();
        for (Usuario u : listUser.values()) {
            aux.add(u);
        }
        return aux;
    }
    
    public List<Usuario> listUsersByDepartment(String name) {
        List<Usuario> aux = new ArrayList();
        for (Usuario u : listUser.values()) {
            if(u.getDepartment().getName().equals(name)){
                 aux.add(u);
            }
           
        }
        return aux;
    }
}
