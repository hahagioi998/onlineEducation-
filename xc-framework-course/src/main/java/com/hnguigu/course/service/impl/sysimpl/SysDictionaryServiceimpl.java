package com.hnguigu.course.service.impl.sysimpl;

import com.hnguigu.course.service.sys.sysDictionaryService;

import com.hnguigu.course.repository.SysDictionaryRepositor;
import com.hnguigu.domain.system.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SysDictionaryServiceimpl implements sysDictionaryService {

    @Autowired
    private SysDictionaryRepositor sysDictionaryRepositor;

    @Override
    public SysDictionary findSysByDtype(String Dtype) {
        SysDictionary sysDictionary = new SysDictionary();
        sysDictionary.setDType(Dtype);
        Example<SysDictionary> example = Example.of(sysDictionary);
        Optional<SysDictionary> one = sysDictionaryRepositor.findOne(example);
        SysDictionary sysDictionary1 = null;
        if(one.isPresent()){
            sysDictionary1 =  one.get();
        }
        return sysDictionary1;
    }
}
