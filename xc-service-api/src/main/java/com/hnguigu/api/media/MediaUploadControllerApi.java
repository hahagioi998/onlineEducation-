package com.hnguigu.api.media;

import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.media.response.CheckChunkResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.management.resource.ResourceRequest;
import org.springframework.web.multipart.MultipartFile;


@Api(value = "媒资管理接口",description = "媒资管理接口，提供文件上传和处理等接口")
public interface MediaUploadControllerApi {
    @ApiOperation("文件上传注册")
    public ResponseResult register(String fileMd5,
                                    String fileName,
                                    Long fileSize,
                                    String fileType,
                                    String fileExt);
    @ApiOperation("验证分块文件是否存在")
    public CheckChunkResult checkChunk(String fileMd5,
                                       Integer chunk,
                                       Integer chunkSize);

    @ApiOperation("上传分块")
    public ResponseResult uploadChunk(MultipartFile file,
                                      String fileMd5,
                                      Integer chunk
                                      );

    @ApiOperation("合并分块")
    public ResponseResult mergeChunks(String fileMd5,
                                       String fileName,
                                       Long fileSize,
                                       String mimetype,
                                       String fileExt);
}
