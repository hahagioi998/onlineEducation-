package com.hnguigu.domain.media.response;

import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.common.model.response.ResultCode;
import com.hnguigu.domain.media.MediaFile;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by mrt on 2018/3/31.
 */
@Data
@NoArgsConstructor
public class MediaFileResult extends ResponseResult {
    MediaFile mediaFile;
    public MediaFileResult(ResultCode resultCode, MediaFile mediaFile) {
        super(resultCode);
        this.mediaFile = mediaFile;
    }
}
