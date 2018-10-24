package com.jk.service.course;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "service-provider")
public interface CourseService extends CourseServiceApi{
}
