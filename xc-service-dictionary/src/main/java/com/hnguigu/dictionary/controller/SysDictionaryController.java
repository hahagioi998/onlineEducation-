package com.hnguigu.dictionary.controller;

import com.hnguigu.api.cms.SysDictionaryControllerApi;
import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.dictionary.service.SysDictionaryService;
import com.hnguigu.domain.system.SysDictionary;
import com.hnguigu.domain.system.request.QueryDictionaryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dictionary")
public class SysDictionaryController implements SysDictionaryControllerApi {
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 条件分页查询
     * @param page
     * @param size
     * @param queryDictionaryRequest
     * @return
     */
    @GetMapping("/list/{page}/{size}")
    @Override
    public QueryResponseResult findDictionary(@PathVariable("page") Integer page, @PathVariable("size") Integer size, QueryDictionaryRequest queryDictionaryRequest){
        return sysDictionaryService.findDictionary(page,size,queryDictionaryRequest);
    }

    /**
     * 根据编号查询数据字典信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @Override
    public SysDictionary findDictionaryById(@PathVariable("id") String id){
        return sysDictionaryService.findDictionaryById(id);
    }

    /**
     * 删除数据字典数据
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    @Override
    public ResponseResult deleteDictionary(@PathVariable("id") String id){
        return sysDictionaryService.deleteDictionary(id);
    }

    /**
     * 添加数据字典
     * @param sysDictionary
     * @return
     */
    @PostMapping("/add")
    @Override
    public ResponseResult addDictionary(@RequestBody SysDictionary sysDictionary){
        return sysDictionaryService.addDictionary(sysDictionary);
    }

    /**
     * 修改数据字典
     * @param sysDictionary
     * @return
     */
    @PutMapping("/update")
    @Override
    public ResponseResult updateDictionary(@RequestBody SysDictionary sysDictionary){
        return sysDictionaryService.updateDictionary(sysDictionary);
    }
}
