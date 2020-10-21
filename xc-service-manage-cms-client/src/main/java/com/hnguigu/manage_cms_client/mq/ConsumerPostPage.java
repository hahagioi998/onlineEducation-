package com.hnguigu.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.hnguigu.domain.cms.CmsPage;
import com.hnguigu.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConsumerPostPage {
    private static final Logger LOGGER= LoggerFactory.getLogger(PageService.class);

    @Autowired
    private PageService pageService;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg){
        System.out.println("***8"+msg);
        //解析消息
        Map map = JSON.parseObject(msg, Map.class);
        String pageId = (String) map.get("pageId");
        CmsPage cmsPage = pageService.findCmsPageById(pageId);

        if (cmsPage==null){
            LOGGER.error("receive postPage msg,CmsPage is null,pageId:{}",pageId);
            return;
        }
        pageService.savePathToServerPath(pageId);
    }
}
