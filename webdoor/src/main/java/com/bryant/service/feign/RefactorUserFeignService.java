package com.bryant.service.feign;

import com.bryant.service.UserFeignService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @FeignClient绑定服务
 * 继承UserFeignService接口
 */
@FeignClient(value = "users")
public interface RefactorUserFeignService extends UserFeignService {

}
