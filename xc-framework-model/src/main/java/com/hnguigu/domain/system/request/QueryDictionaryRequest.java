package com.hnguigu.domain.system.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 14:59
 **/
@Data
public class QueryDictionaryRequest {
    //接收数据字典查询的查询条件

    private Integer page;

    private Integer size;

    private String dName;

}
