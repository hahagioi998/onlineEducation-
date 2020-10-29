package com.hnguigu.order.controller;

import com.hnguigu.api.order.OrderControllerApi;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.domain.order.XcOrders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/order")
@CrossOrigin
public class OrderController implements OrderControllerApi {

    @PostMapping("/list/{page}/{size}")
    @ResponseBody
    @Override
    public QueryResult<XcOrders> findorderPages(@PathVariable(value = "page") Integer pageNum,@PathVariable(value = "size") Integer pageSize, Map<String, Object> map) {
        int i = pageNum + pageSize;
        return null;
    }
}
