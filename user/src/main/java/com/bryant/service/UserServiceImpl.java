package com.bryant.service;

import com.bryant.mapper.UserMapper;
import com.bryant.model.UserDetail;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.annotation.Resources;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetail insert(UserDetail detail) {
        if (Objects.isNull(detail)) {
            throw new RuntimeException("detail is null");
        }
        userMapper.insert(detail);
        return detail;
    }

    @Override
    public UserDetail getById(Long id) {
        return userMapper.getById(id);
    }

    @Override
    public void update(UserDetail detail) {
        userMapper.updateById(detail);
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteById(id);
    }
}
