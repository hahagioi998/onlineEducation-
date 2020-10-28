package com.hnguigu.api.course;

import com.hnguigu.domain.filesystem.response.UploadFileResult;
import org.springframework.web.multipart.MultipartFile;

public interface FilesystemControllerApi {

    public UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata);

    public UploadFileResult teacherupload(MultipartFile multipartFile, String filetag, String businesskey, String metadata);
}
