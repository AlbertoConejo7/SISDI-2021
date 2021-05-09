package com.sisdi.service;

import com.sisdi.model.UserEntity;
import java.util.List;

public interface UserService {
    List<UserEntity> listUsers();
    UserEntity addUser(UserEntity u);
    UserEntity getUser(String u);
}
