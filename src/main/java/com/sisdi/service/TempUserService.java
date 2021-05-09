package com.sisdi.service;

import com.sisdi.model.TempUser;
import java.util.List;

public interface TempUserService {
    List<TempUser> listarTempUser();
    TempUser addTempUser(TempUser u);
}
