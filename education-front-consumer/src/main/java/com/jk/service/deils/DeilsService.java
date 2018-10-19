package com.jk.service.deils;

import com.jk.model.education.ClassBean;
import com.jk.service.education.EduServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "front-provider")
public interface DeilsService extends EduServiceApi {
}
