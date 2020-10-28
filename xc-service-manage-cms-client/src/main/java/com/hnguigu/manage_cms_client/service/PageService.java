package com.hnguigu.manage_cms_client.service;

import com.hnguigu.domain.cms.CmsPage;
import com.hnguigu.domain.cms.CmsSite;
import com.hnguigu.manage_cms_client.dao.CmsPageRepository;
import com.hnguigu.manage_cms_client.dao.CmsSiteRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class PageService {

    private static final Logger LOGGER=LoggerFactory.getLogger(PageService.class);

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;


    //保存html页面到服务器物理路径
    public void savePathToServerPath(String id){
        CmsPage cmsPage = this.findCmsPageById(id);
        String htmlFileId = cmsPage.getHtmlFileId();

        InputStream inputStream = this.getFileById(htmlFileId);

        if (inputStream==null){
            LOGGER.error("getFileById InputStream is null, htmlFileId:{}",htmlFileId);
        }

        CmsSite cmsSite = this.findCmsSiteById(cmsPage.getSiteId());

        String sitePhysicalPath = cmsSite.getSitePhysicalPath();

        String pagePath=sitePhysicalPath+cmsPage.getPagePhysicalPath()+"/"+cmsPage.getPageName();
        System.out.println(pagePath);
        File file=new File(sitePhysicalPath+cmsPage.getPagePhysicalPath());

        file.mkdirs();
        FileOutputStream fileOutputStream=null;

        try {
            fileOutputStream=new FileOutputStream(new File(pagePath));

             IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //根据文件id从GridFS中查询文件
    public InputStream getFileById(String fileId){
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));

        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据页面id查询页面信息
    public CmsPage findCmsPageById(String id){
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    //根据站点id查询站点信息
    public CmsSite findCmsSiteById(String siteId){
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }
}
