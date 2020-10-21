package com.hnguigu.api.cms;


import com.hnguigu.domain.cms.CmsTemplate;

import java.util.List;

public interface CmsTemplateControllerApi {
    /**
     * 查询所有模板
     * @return
     */
    List<CmsTemplate> queryAllTemplate();
}
