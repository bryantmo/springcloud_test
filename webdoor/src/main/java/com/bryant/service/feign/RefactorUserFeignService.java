package com.bryant.service.feign;

import com.bryant.service.UserFeignService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * （1）configuration = DisableHystrixConfiguration，关闭Hystrix；
 * 但是，feign.hystrix.enabled=true的全局配置生效时，还是会开启Hystrix；
 * （2）RefactorUserFeignFallback是服务降级实现类
 * @FeignClient绑定服务
 * 继承UserFeignService接口
 */
@FeignClient(name = "users2") // 权宜之计，为了避免和RefactorUserFeignService冲突
public interface RefactorUserFeignService extends UserFeignService {


}