package com.bryant.command;

import com.bryant.constants.Web;
import com.bryant.model.User;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

/**
 * Hystrix命令：封装具体的依赖服务调用逻辑
 */
public class UserServiceCommand extends HystrixCommand<User> {

    private RestTemplate restTemplate;
    private Long id;

    protected UserServiceCommand(Setter setter, RestTemplate restTemplate, Long id) {
        super(setter);
        this.restTemplate = restTemplate;
        this .id = id;
    }

    @Override
    protected User run() throws Exception {
        String uri = Web.SERVICE_USERS_URL + "/getNameByUserId";
        String name = restTemplate.getForObject(uri, String.class);
        return new User(1, name);
    }

    /**
     * 四种情况：
     * 错误
     * 延迟
     * 线程池拒绝
     * 断路器熔断
     * @return
     */
    @Override
    protected User getFallback() {
        return new User(2, "fallback");
    }

    @Override
    protected String getCacheKey() {
        return super.getCacheKey();
    }
}
