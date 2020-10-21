package com.hnguigu.dictionary.service;

import com.hnguigu.common.model.response.*;
import com.hnguigu.dictionary.dao.SysDictionaryRepository;
import com.hnguigu.domain.system.SysDictionary;
import com.hnguigu.domain.system.SysDictionaryValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.ExampleMatcherAccessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SysDictionaryService {
    @Autowired
    private SysDictionaryRepository sysDictionaryRepository;

    /**
     * 条件分页查询
     * @param page
     * @param size
     * @param name
     * @return
     */
    public QueryResponseResult findDictionary(Integer page, Integer size, String name){
        PageRequest pageRequest = PageRequest.of(page, size);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("dName", ExampleMatcher.GenericPropertyMatchers.contains());

        SysDictionary sysDictionary=new SysDictionary();
        sysDictionary.setDName(name);

        Example example=Example.of(sysDictionary,exampleMatcher);

        Page all = sysDictionaryRepository.findAll(example, pageRequest);

        QueryResult queryResult=new QueryResult();
        queryResult.setTotal(all.getTotalElements());
        queryResult.setList(all.getContent());

        QueryResponseResult<Object> queryResponseResult = new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);

        return queryResponseResult;
    }

    /**
     * 根据编号查询数据字典信息
     * @param id
     * @return
     */
    public SysDictionary findDictionaryById(String id){
        Optional<SysDictionary> optional = sysDictionaryRepository.findById(id);

        if (optional.isPresent()){
            return optional.get();
        }

        return null;
    }

    /**
     * 删除数据字典数据
     * @param id
     * @return
     */
    public ResponseResult deleteDictionary(String id){
        SysDictionary sysDictionary = this.findDictionaryById(id);
        if (sysDictionary!=null){
            sysDictionaryRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }

        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 添加数据字典
     * @param sysDictionary
     * @return
     */
    public ResponseResult addDictionary(SysDictionary sysDictionary){
        SysDictionary save = sysDictionaryRepository.save(sysDictionary);
        if (save!=null){
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 修改数据字典
     * @param sysDictionary
     * @return
     */
    public ResponseResult updateDictionary(SysDictionary sysDictionary){
        SysDictionary sd = this.findDictionaryById(sysDictionary.getId());

        if (sd!=null){
            sd.setDName(sysDictionary.getDName());
            sd.setDType(sysDictionary.getDType());
            sd.setDValue(sysDictionary.getDValue());
            sd.setId(sysDictionary.getId());
            SysDictionary save = sysDictionaryRepository.save(sd);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

}
