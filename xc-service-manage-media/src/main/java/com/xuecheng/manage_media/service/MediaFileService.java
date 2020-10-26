package com.xuecheng.manage_media.service;

import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.domain.media.MediaFile;
import com.hnguigu.domain.media.request.QueryMediaFileRequest;
import com.xuecheng.manage_media.dao.MediaFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaFileService {
    @Autowired
    MediaFileRepository mediaFileRepository;

    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        if(queryMediaFileRequest==null){
                queryMediaFileRequest=new QueryMediaFileRequest();
        }
        //条件值对象
        MediaFile mediaFile=new MediaFile();
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getTag())){
                mediaFile.setTag(queryMediaFileRequest.getTag());
        }
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())){
            mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
        }
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getProcessStatus())){
            mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
        }
        //条件匹配器
        ExampleMatcher exampleMatcher=ExampleMatcher.matching()
                                                    .withMatcher("tag",ExampleMatcher.GenericPropertyMatchers.contains())
                                                    .withMatcher("fileOriginalName",ExampleMatcher.GenericPropertyMatchers.contains());

        //定义example条件
        Example<MediaFile> example = Example.of(mediaFile, exampleMatcher);
        //分页查询对象
        if(page<=0){
             page=1;
        }
        page=page-1;
        if(size<=0){
            size=10;
        }
        Pageable pageable=new PageRequest(page,size);
        //分页查询
        Page<MediaFile> mediaFiles = mediaFileRepository.findAll(example, pageable);
        //总记录数
        long totalElements = mediaFiles.getTotalElements();
        //数据列表
        List<MediaFile> content = mediaFiles.getContent();
        QueryResult<MediaFile> queryResult=new QueryResult<>();
        queryResult.setList(content);
        queryResult.setTotal(totalElements);
        QueryResponseResult<MediaFile> queryResponseResult=new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);
        return  queryResponseResult;
    }
}
