package com.jk.service.user;

import com.jk.model.user.RoleBean;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(value = "service-provider")
public interface UserService extends UserServiceApi {

}
