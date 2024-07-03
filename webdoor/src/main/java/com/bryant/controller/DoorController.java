package com.bryant.controller;

import com.bryant.constants.Web;
import com.bryant.model.User;
import java.net.URI;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class DoorController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * #restTemplate.getForObject,是对getForObject的进一步封装
     * 好处：少一步从Response获取body的步骤
     * 也有三种重载方法
     * @return
     */
    @GetMapping("/getName")
    public String getName(
    ) {
        String uri = Web.SERVICE_USERS_URL + "/getNameByUserId";
        return restTemplate.getForObject(uri, String.class);
    }

    /**
     * #restTemplate.getForEntity
     * 请求通过 占位符 和 参数列表绑定
     * 返回的是基本类型
     * @param userId
     * @return
     */
    @GetMapping("/getNameByUserId")
    public String getNameByUserId(
            @RequestParam(value = "userId") Integer userId
    ) {
        String uri = Web.SERVICE_USERS_URL + "/getNameByUserId?userId={1}";
        return restTemplate.getForEntity(uri, String.class, userId).getBody();
    }

    /**
     * #restTemplate.getForEntity
     * 请求通过 占位符 和 参数列表绑定
     * 返回的是对象
     * @param userId
     * @return
     */
    @GetMapping("/getUserByUserId")
    public User getUserByUserId(
            @RequestParam(value = "userId") Integer userId
    ) {
        String uri = Web.SERVICE_USERS_URL + "/getUserByUserId?userId={1}";
        return restTemplate.getForEntity(uri, User.class, userId).getBody();
    }

    /**
     * #restTemplate.getForEntity
     * 请求通过 Map 和 参数列表绑定
     * 返回的是对象
     * @param userName
     * @return
     */
    @GetMapping("/getUserByUserName")
    public User getUserByUserName(
            @RequestParam(value = "userName") String userName
    ) {
        String uri = Web.SERVICE_USERS_URL + "/getUserByUserName?userName={userName}";
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", userName);
        return restTemplate.getForEntity(uri, User.class, params).getBody();
    }

    /**
     * 使用了 UriComponents 去获取 URI，并请求服务端
     * 通过URI 绑定 key/value
     * @param userName
     * @return
     */
    @GetMapping("/getUserByUserName2")
    public User getUserByUserName2(
            @RequestParam(value = "userName") String userName
    ) {
        UriComponents uriComponents =
                UriComponentsBuilder
                .fromUriString(Web.SERVICE_USERS_URL + "/getUserByUserName?userName={userName}")
                .build().expand("bryant").encode();
        URI uri = uriComponents.toUri();
        return restTemplate.getForEntity(uri, User.class).getBody();
    }

    /**
     * #restTemplate.postForEntity
     * 三种重载方法：
     *  1. 数组 -> value
     *  3. Map -> body
     *  3. 对象 -> body
     * @param userId
     * @param userName
     * @return
     */
    @GetMapping("/createUser")
    public User createUser(
            @RequestParam("userId") Integer userId,
            @RequestParam("userName") String userName
    ) {
        String uri = Web.SERVICE_USERS_URL + "/createUser";
        User user = new User(userId, userName);
        return restTemplate.postForEntity(uri, user, User.class).getBody();
    }

    @GetMapping("/createUserV2")
    public User createUserV2(
            @RequestParam("userId") Integer userId,
            @RequestParam("userName") String userName
    ) {
        String uri = Web.SERVICE_USERS_URL + "/createUser";
        User user = new User(userId, userName);
        return restTemplate.postForObject(uri, user, User.class);
    }

    @GetMapping("/createUserV3")
    public URI createUserV3(
            @RequestParam("userId") Integer userId,
            @RequestParam("userName") String userName
    ) {
        String uri = Web.SERVICE_USERS_URL + "/createUser";
        User user = new User(userId, userName);
        return restTemplate.postForLocation(uri, user);
    }

    @GetMapping("/modifyUser")
    public User modifyUser(
            @RequestParam("userId") Integer userId,
            @RequestParam("userName") String userName
    ) {
        String uri = Web.SERVICE_USERS_URL + "/modifyUser/{1}";
        Integer id = 111;
        User user = new User(userId, userName);
        restTemplate.put(uri, user, id);
        return user;
    }

    @GetMapping("/deleteUser")
    public void deleteUser(
            @RequestParam("userId") Integer userId
    ) {
        String uri = Web.SERVICE_USERS_URL + "/deleteUser/{1}";
        restTemplate.delete(uri, userId);
    }
}
