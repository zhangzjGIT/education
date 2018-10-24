package com.jk.service.adverService;

import com.jk.service.advertising.AdverServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "service-provider")
public interface AdverService  extends AdverServiceApi {
}
