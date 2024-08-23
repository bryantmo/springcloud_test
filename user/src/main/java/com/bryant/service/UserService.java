package com.bryant.service;

import com.bryant.model.UserDetail;

public interface UserService {

    UserDetail insert(UserDetail detail);

    UserDetail getById(Long id);

    void update(UserDetail detail);

    void delete(Long id);

}
