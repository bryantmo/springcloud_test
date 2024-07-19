package com.bryant.controller;

import com.bryant.model.User;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/getName")
    public String getName() {
        return "test_with_none";
    }

    @GetMapping("/getNameByUserId")
    public String getNameByUserId(
            @RequestParam(value = "userId", defaultValue = "1") Integer userId
    ) {
        return String.format("your name is %s", userId);
    }

    @GetMapping("/getUserByUserId")
    public User getUserByUserId(
            @RequestParam(value = "userId", defaultValue = "1") Integer userId
    ) {
        return User.builder().userId(userId).name("test").build();
    }

    @GetMapping("/getUserByUserName")
    public User getUserByUserName(
            @RequestParam(value = "userName", defaultValue = "aaa") String userName
    ) {
        return User.builder()
                .userId(new Random().nextInt(10000))
                .name(userName).build();
    }

    @PostMapping("/createUser")
    public User createUser(
            @RequestBody User user) {
        return User.builder()
                .userId(user.getUserId())
                .name(user.getName()).build();
    }

    @PutMapping("/modifyUser/{userId}")
    public void modifyUser(
            @RequestBody User user,
            @RequestParam Integer userId
    ){
        System.out.println(String.format("modify user, userId: %s, name: %s",
                userId, user.getName()));
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(
            @RequestParam Integer userId
    ){
        System.out.println(String.format("delete user, userId: %s", userId));
    }

    @GetMapping("/getUserByUserNames")
    public List<User> getUserByUserNames(
            @RequestParam List<String> usernames
    ) {
        return Arrays.asList(new User( "test", 1), new User("test2", 2));
    }

}
