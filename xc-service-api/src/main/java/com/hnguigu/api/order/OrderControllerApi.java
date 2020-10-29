package com.hnguigu.api.order;

import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.domain.order.XcOrders;

import java.util.Map;

public interface OrderControllerApi {

    QueryResult<XcOrders> findorderPages(Integer pageNum, Integer pageSize, Map<String,Object> map);
}
