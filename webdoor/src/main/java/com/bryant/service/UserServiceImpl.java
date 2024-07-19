package com.bryant.service;

import static java.util.Arrays.asList;

import com.bryant.constants.Web;
import com.bryant.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "testHystrixFallback")
    @Override
    public String testHystrix(String name) {
        String uri = Web.SERVICE_USERS_URL + "/getNameByUserId";
        return restTemplate.getForObject(uri, String.class);
    }

    public String testHystrixFallback(String name) {
        return "testHystrixFallback";
    }

    /**
     * BadRequestException会包装再HystrixBadRequestException里面抛出，不触发fallback逻辑。
     * @param name
     * @return
     * @throws InterruptedException
     */
    @HystrixCommand(fallbackMethod = "testHystrixTimeOutFallback", ignoreExceptions = HystrixBadRequestException.class)
    @Override
    public String testHystrixTimeOut(String name) throws InterruptedException {
        String uri = Web.SERVICE_USERS_URL + "/getNameByUserId";
        int sleepTime = new Random().nextInt(3000);
        log.info("sleepTime:{}", sleepTime);
        Thread.sleep(sleepTime);

        return restTemplate.getForObject(uri, String.class);
    }

    public String testHystrixTimeOutFallback(String name) {
        return "testHystrixTimeOutFallback";
    }

    @HystrixCommand(fallbackMethod = "testHystrixExceptionFallback")
    @Override
    public String testHystrixException(String name) throws Exception {
        throw new RuntimeException("testHystrixException");
    }

    public String testHystrixExceptionFallback(String name, Throwable e) {
        log.error("testHystrixExceptionFallback, {}", e.getStackTrace());
        return "testHystrixExceptionFallback";
    }


    @HystrixCommand(fallbackMethod = "testHystrixWithGroupFallback", commandKey = "command1", groupKey = "group1", threadPoolKey = "threadPool1")
    @Override
    public String testHystrixWithGroup(String name) throws Exception {
        throw new RuntimeException("testHystrixException");
    }

    public String testHystrixWithGroupFallback(String name, Throwable e) {
        log.error("testHystrixExceptionFallback, {}", e.getStackTrace());
        return "testHystrixExceptionFallback";
    }

    /**
     * 单次请求，注意入参和返回值，都是单个
     */
    @HystrixCollapser(batchMethod = "getUserByUserNames",
            collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds", value = "10000")})
    @Override
    public User getUserByUserNameByCollapser(String userName) {
        log.info("getUserByUserNameByCollapser, {}", userName);
        return null;
    }

    /**
     * 请求合并，注意入参和返回值，都是集合
     */
    @HystrixCommand
    public List<User> getUserByUserNames(List<String> userNames) {
        String uri = Web.SERVICE_USERS_URL + "/getUserByUserNames?userNames={1}";
        return asList(restTemplate.getForObject(uri, User.class, StringUtils.join(userNames, ",")));
    }

}
