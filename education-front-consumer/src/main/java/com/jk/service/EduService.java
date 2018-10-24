package com.jk.service;

import com.jk.service.education.EduServiceApi;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @Auther: yangjianguang
 * @Date: 2018/10/17 09:55
 * @Description:
 */
@FeignClient(value = "front-provider")
public interface EduService extends EduServiceApi {

}
