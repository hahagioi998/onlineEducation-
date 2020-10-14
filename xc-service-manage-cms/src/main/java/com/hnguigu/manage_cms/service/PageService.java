package com.hnguigu.manage_cms.service;

import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.cms.CmsConfig;
import com.hnguigu.domain.cms.CmsPage;
import com.hnguigu.domain.cms.request.QueryPageRequest;
import com.hnguigu.domain.cms.response.CmsPageResult;
import com.hnguigu.manage_cms.dao.CmsConfigRepository;
import com.hnguigu.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    /**
     * 页面查询方法
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    public QueryResponseResult findList(Integer page, Integer size, QueryPageRequest queryPageRequest) {
        PageRequest pageable=  PageRequest.of(page-1,size);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher
                ("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        CmsPage cmsPage = new CmsPage();
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //定义条件对象Example
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        QueryResult queryResult=new QueryResult();
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        QueryResponseResult queryResponseResult=new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

    /**
     * 添加页面
     * @param cmsPage
     * @return
     */
    public CmsPageResult addPage(CmsPage cmsPage) {
        CmsPage cm = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath
                (cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (cm==null){
            cmsPage.setPageId(null);
            CmsPage save = cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
        }

        return new CmsPageResult(CommonCode.SUCCESS,null);
    }

    /**
     * 修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult updatePage(String id,CmsPage cmsPage) {
        CmsPage cm = this.findById(id);
        if(cm!=null){
            cm.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            cm.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            cm.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            cm.setPageName(cmsPage.getPageName());
            //更新访问路径
            cm.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            cm.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新dataUrl
            cm.setDataUrl(cmsPage.getDataUrl());
            //提交修改
            cmsPageRepository.save(cm);
            return new CmsPageResult(CommonCode.SUCCESS,cm);
        }
        //修改失败
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    /**
     * 根据编号查询页面信息
     * @param id
     * @return
     */
    public CmsPage findById(String id) {
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 根据编号查询页面信息
     * @param id
     * @return
     */
    public ResponseResult deletePage(String id){
        CmsPage cm = this.findById(id);
        if (cm!=null){
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    public CmsConfig getConfigById(String id){
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }


}
