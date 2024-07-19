package com.bryant.service;

import com.bryant.model.User;

public interface UserService {

    String testHystrix(String name);

    String testHystrixTimeOut(String name) throws InterruptedException;

    String testHystrixException(String name) throws Exception;

    String testHystrixWithGroup(String name) throws Exception;

    User getUserByUserNameByCollapser(String userName);
}
