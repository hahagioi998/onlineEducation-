package com.hnguigu.api.cms;

import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.system.SysDictionary;

public interface SysDictionaryControllerApi {
    /**
     * 条件分页查询
     * @param page
     * @param size
     * @param name
     * @return
     */
    public QueryResponseResult findDictionary(Integer page, Integer size, String name);

    /**
     * 根据编号查询数据字典信息
     * @param id
     * @return
     */
    public SysDictionary findDictionaryById(String id);

    /**
     * 删除数据字典数据
     * @param id
     * @return
     */
    public ResponseResult deleteDictionary(String id);

    /**
     * 添加数据字典
     * @param sysDictionary
     * @return
     */
    public ResponseResult addDictionary(SysDictionary sysDictionary);

    /**
     * 修改数据字典
     * @param sysDictionary
     * @return
     */
    public ResponseResult updateDictionary(SysDictionary sysDictionary);
}
