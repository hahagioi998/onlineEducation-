package com.hnguigu.api.media;

import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.domain.media.MediaFile;
import com.hnguigu.domain.media.request.QueryMediaFileRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "媒资文体管理",description = "媒资文体管理接口",tags = {"媒体文件管理接口"})
public interface MediaFileControllerApi {
    @ApiOperation("我的问题媒资文件查询列表")
    public QueryResponseResult<MediaFile>  findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);
}
