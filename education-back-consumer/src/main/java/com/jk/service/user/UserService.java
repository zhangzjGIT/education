package com.jk.service.user;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "service-provider")
public interface UserService extends UserServiceApi {
}
