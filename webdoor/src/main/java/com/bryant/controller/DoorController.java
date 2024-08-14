//package com.bryant.controller;
//
//import com.bryant.constants.Web;
//import com.bryant.model.User;
//import com.bryant.service.UserService;
//import com.bryant.service.feign.RefactorUserFeignService;
//import com.bryant.service.feign.UserFeignService;
//import com.bryant.service.feign.fallback.UserFeignHystrixService;
//import java.net.URI;
//import java.util.HashMap;
//import java.util.Random;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponents;
//import org.springframework.web.util.UriComponentsBuilder;
//
//@RestController
//public class DoorController {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private UserService userService;
//
//    /**
//     * feign（Feign-RPC接口在微服务内部定义）
//     */
//    @Autowired
//    private UserFeignService userFeignService;
//
//    /**
//     * feign重构（RPC抽象到user-feign-service-api）
//     */
//    @Autowired
//    private RefactorUserFeignService refactorUserFeignService;
//
//    /**
//     * feign整合hystrix（通过RefactorUserFeignFallback）
//     */
//    @Autowired
//    private UserFeignHystrixService userFeignHystrixService;
//
//    @GetMapping("/test/user/sleuth2")
//    public String getNameFeign2() {
//        return "/test/user/sleuth2, " + userFeignService.getName();
//    }
//
//    @GetMapping("/test/user/sleuth")
//    public String getNameFeign() {
//        return "/test/user/sleuth, " + refactorUserFeignService.getName();
//    }
//
//    @GetMapping("/test/refactor/v2/getName")
//    public String getNameFeignHystrix() {
//        return userFeignHystrixService.testHystrixV2();
////        return "";
//    }
//
//    @GetMapping("/refactor/testHystrixV2")
//    public String testHystrixv2() {
//        return refactorUserFeignService.testHystrixV2();
//    }
//
//    @GetMapping("/refactor/getName")
//    public String getNameRefactor() {
//        return refactorUserFeignService.getName();
//    }
//
//    @GetMapping("/refactor/feign/getUser")
//    public String getUserRefactor() throws Exception {
//        return refactorUserFeignService.getUser("bryant000");
//    }
//
//    @GetMapping("/refactor/feign/getUser2")
//    public com.bryant.dto.User getUser2Refactor() {
//        return refactorUserFeignService.getUser2("bryant111", 11);
//    }
//
//    @GetMapping("/refactor/feign/getUser3")
//    public String getUser3Refactor() {
//        return refactorUserFeignService.getUser3(new com.bryant.dto.User(333, "bryant333"));
//    }
//
//    @GetMapping("/testGetNameByFeign")
//    public String testGetNameByFeign() {
//        return userFeignService.getName();
//    }
//
//    @GetMapping("/feign/getUser")
//    public String getUserV1() {
//        return userFeignService.getUser("bryant11");
//    }
//
//    @GetMapping("/feign/getUser2")
//    public String getUserV11() {
//        return userFeignService.getUser2(
//               "bryant", 111
//        );
//    }
//
//    @GetMapping("/feign/getUser3")
//    public String getUser3V1() {
//        return userFeignService.getUser3(
//                new User(222, "bryant")
//        );
//    }
//
//    @GetMapping("/testHystrix")
//    public String testHystrixV1() {
//        return userService.testHystrix("bryant");
//    }
//
//    @GetMapping("/testHystrixTimeOut")
//    public String testHystrixTimeOut() throws InterruptedException {
//        return userService.testHystrixTimeOut("bryant");
//    }
//
//    @GetMapping("/testHystrixException")
//    public String testHystrixException() throws Exception {
//        return userService.testHystrixException("bryant");
//    }
//
//    @GetMapping("/testHystrixWithGroup")
//    public String testHystrixWithGroup() throws Exception {
//        return userService.testHystrixWithGroup("bryant");
//    }
//
//    @GetMapping("/getUserByUserNameByCollapser")
//    public User getUserByUserNameByCollapser() {
//        return userService.getUserByUserNameByCollapser("bryant" + new Random().nextInt(100));
//    }
//
//    /**
//     * #restTemplate.getForObject,是对getForObject的进一步封装
//     * 好处：少一步从Response获取body的步骤
//     * 也有三种重载方法
//     * @return
//     */
//    @GetMapping("/getName")
//    public String getName(
//    ) {
//        String uri = Web.SERVICE_USERS_URL + "/getNameByUserId";
//        return restTemplate.getForObject(uri, String.class);
//    }
//
//    /**
//     * #restTemplate.getForEntity
//     * 请求通过 占位符 和 参数列表绑定
//     * 返回的是基本类型
//     * @param userId
//     * @return
//     */
//    @GetMapping("/getNameByUserId")
//    public String getNameByUserId(
//            @RequestParam(value = "userId") Integer userId
//    ) {
//        String uri = Web.SERVICE_USERS_URL + "/getNameByUserId?userId={1}";
//        return restTemplate.getForEntity(uri, String.class, userId).getBody();
//    }
//
//    /**
//     * #restTemplate.getForEntity
//     * 请求通过 占位符 和 参数列表绑定
//     * 返回的是对象
//     * @param userId
//     * @return
//     */
//    @GetMapping("/getUserByUserId")
//    public User getUserByUserId(
//            @RequestParam(value = "userId") Integer userId
//    ) {
//        String uri = Web.SERVICE_USERS_URL + "/getUserByUserId?userId={1}";
//        return restTemplate.getForEntity(uri, User.class, userId).getBody();
//    }
//
//    /**
//     * #restTemplate.getForEntity
//     * 请求通过 Map 和 参数列表绑定
//     * 返回的是对象
//     * @param userName
//     * @return
//     */
//    @GetMapping("/getUserByUserName")
//    public User getUserByUserName(
//            @RequestParam(value = "userName") String userName
//    ) {
//        String uri = Web.SERVICE_USERS_URL + "/getUserByUserName?userName={userName}";
//        HashMap<String, String> params = new HashMap<>();
//        params.put("userName", userName);
//        return restTemplate.getForEntity(uri, User.class, params).getBody();
//    }
//
//    /**
//     * 使用了 UriComponents 去获取 URI，并请求服务端
//     * 通过URI 绑定 key/value
//     * @param userName
//     * @return
//     */
//    @GetMapping("/getUserByUserName2")
//    public User getUserByUserName2(
//            @RequestParam(value = "userName") String userName
//    ) {
//        UriComponents uriComponents =
//                UriComponentsBuilder
//                .fromUriString(Web.SERVICE_USERS_URL + "/getUserByUserName?userName={userName}")
//                .build().expand("bryant").encode();
//        URI uri = uriComponents.toUri();
//        return restTemplate.getForEntity(uri, User.class).getBody();
//    }
//
//    /**
//     * #restTemplate.postForEntity
//     * 三种重载方法：
//     *  1. 数组 -> value
//     *  3. Map -> body
//     *  3. 对象 -> body
//     * @param userId
//     * @param userName
//     * @return
//     */
//    @GetMapping("/createUser")
//    public User createUser(
//            @RequestParam("userId") Integer userId,
//            @RequestParam("userName") String userName
//    ) {
//        String uri = Web.SERVICE_USERS_URL + "/createUser";
//        User user = new User(userId, userName);
//        return restTemplate.postForEntity(uri, user, User.class).getBody();
//    }
//
//    @GetMapping("/createUserV2")
//    public User createUserV2(
//            @RequestParam("userId") Integer userId,
//            @RequestParam("userName") String userName
//    ) {
//        String uri = Web.SERVICE_USERS_URL + "/createUser";
//        User user = new User(userId, userName);
//        return restTemplate.postForObject(uri, user, User.class);
//    }
//
//    @GetMapping("/createUserV3")
//    public URI createUserV3(
//            @RequestParam("userId") Integer userId,
//            @RequestParam("userName") String userName
//    ) {
//        String uri = Web.SERVICE_USERS_URL + "/createUser";
//        User user = new User(userId, userName);
//        return restTemplate.postForLocation(uri, user);
//    }
//
//    @GetMapping("/modifyUser")
//    public User modifyUser(
//            @RequestParam("userId") Integer userId,
//            @RequestParam("userName") String userName
//    ) {
//        String uri = Web.SERVICE_USERS_URL + "/modifyUser/{1}";
//        Integer id = 111;
//        User user = new User(userId, userName);
//        restTemplate.put(uri, user, id);
//        return user;
//    }
//
//    @GetMapping("/deleteUser")
//    public void deleteUser(
//            @RequestParam("userId") Integer userId
//    ) {
//        String uri = Web.SERVICE_USERS_URL + "/deleteUser/{1}";
//        restTemplate.delete(uri, userId);
//    }
//}
