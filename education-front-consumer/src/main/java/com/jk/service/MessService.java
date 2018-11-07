package com.jk.service;

import com.jk.service.solr.MessServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 类描述：  搜索
 * 创建人：LDW
 * 创建时间：2018/10/26 10:49
 * 修改人：LDW
 * 修改时间：2018/10/26 10:49
 * 修改备注：
 *
 * @version ：1.0
 */
@FeignClient(value = "front-provider", fallback = MessError.class)
public interface MessService extends MessServiceApi {
}
