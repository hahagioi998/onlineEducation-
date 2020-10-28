package com.hnguigu.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.hnguigu.common.exception.ExceptionCast;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.QueryResponseResult;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.domain.cms.CmsConfig;
import com.hnguigu.domain.cms.CmsPage;
import com.hnguigu.domain.cms.CmsSite;
import com.hnguigu.domain.cms.CmsTemplate;
import com.hnguigu.domain.cms.request.QueryPageRequest;
import com.hnguigu.domain.cms.response.CmsCode;
import com.hnguigu.domain.cms.response.CmsPageResult;
import com.hnguigu.domain.cms.response.CmsPostPageResult;
import com.hnguigu.manage_cms.config.RabbitmqConfig;
import com.hnguigu.manage_cms.dao.CmsConfigRepository;
import com.hnguigu.manage_cms.dao.CmsPageRepository;
import com.hnguigu.manage_cms.dao.CmsSiteRepository;
import com.hnguigu.manage_cms.dao.CmsTemplateRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

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
            return new CmsPageResult(CommonCode.SUCCESS,save);
        }

        return new CmsPageResult(CommonCode.SUCCESS,cm);
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
     * 根据编号删除页面信息
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

    public String getHtml(String id){
        Map model = getModelByPageId(id);
        if (model==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        String template = getTeampleteById(id);
        if (template==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        String html = generateHtml(template, model);
        return html;
    }

    //执行静态化
    private String generateHtml(String templateContent,Map model ){
        //创建配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //创建模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateContent);
        //向configuration配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板
        try {
            Template template = configuration.getTemplate("template");
            //调用api进行静态化
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //获取页面模型信息
    private String getTeampleteById(String id){
        CmsPage cm = this.findById(id);
        if(cm==null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        String templateId = cm.getTemplateId();

        if (StringUtils.isEmpty(templateId)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if (optional.isPresent()){
            CmsTemplate cmsTemplate = optional.get();
            //根据文件id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(cmsTemplate.getTemplateFileId())));

            //打开一个下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            //创建GridFsResource对象，获取流
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            //从流中取数据
            try {
                String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
                return content;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //获取数据模型
    private Map getModelByPageId(String id){
        CmsPage cm = this.findById(id);
        Map map=new HashMap();
        if(cm==null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        String dataUrl = cm.getDataUrl();

        if (StringUtils.isEmpty(dataUrl)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map map2 = forEntity.getBody();
        map.put("model",map2);
        return  map;
    }

    //页面发布
    public ResponseResult post(String pageId){
        String html = this.getHtml(pageId);

        CmsPage cmsPage = saveHtml(pageId, html);

        sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //保存html到GridFs
    private CmsPage saveHtml(String pageId,String htmlContent){

        CmsPage cmsPage = this.findById(pageId);

        if (cmsPage==null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        ObjectId objectId=null;
        try {
            InputStream inputStream = IOUtils.toInputStream(htmlContent, "utf-8");
            objectId=gridFsTemplate.store(inputStream,cmsPage.getPageName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        cmsPage.setHtmlFileId(objectId.toHexString());
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }

    //向mq发送消息
    public void sendPostPage(String pageId){
        CmsPage cmsPage = this.findById(pageId);

        Map<String,String> msg=new HashMap<>();

        msg.put("pageId",pageId);

        String jsonString = JSON.toJSONString(msg);
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,cmsPage.getSiteId(),jsonString);
    }

    //查询所有的站点
    public List<CmsSite> queryAllSite(){
        return cmsSiteRepository.findAll();
    }

    //查询所有的站点
    public List<CmsTemplate> queryAllTemplate(){
        return cmsTemplateRepository.findAll();
    }
    //保存页面，有则更新，没有则添加
    public CmsPageResult save(CmsPage cmsPage) {
        CmsPage byPageNameAndSiteIdAndPageWebPath = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath
                (cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        if(byPageNameAndSiteIdAndPageWebPath!=null){
               return  this.updatePage(byPageNameAndSiteIdAndPageWebPath.getPageId(),cmsPage);
        }
        return this.addPage(cmsPage);
    }

    //一键发布页面
    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        //将页面的信息保存在cms_page集合中
        CmsPageResult save = this.addPage(cmsPage);
        if(!save.isSuccess()){
            ExceptionCast.cast(CommonCode.FAIL);
        }

        //通过if后 拿到页面的id
        CmsPage cmsPageSave = save.getCmsPage();
        String pageId = cmsPageSave.getPageId();

        //执行页面发布(静态化、保存GridFS，向MQ发送消息)
        ResponseResult post = this.post(pageId);
        if(!post.isSuccess()){
            ExceptionCast.cast(CommonCode.FAIL);
        }

        //拼接页面URL
        //取出站点id
        String siteId = cmsPageSave.getSiteId();
        CmsSite cmsSite = this.findCmsSiteById(siteId);

        //拼装URL
        String pageUrl = cmsSite.getSiteDomain() + cmsSite.getSiteWebPath() + cmsPageSave.getPageWebPath() + cmsPageSave.getPageName();
        return new CmsPostPageResult(CommonCode.SUCCESS,pageUrl);
    }

    //根据id查询站点信息
    public CmsSite findCmsSiteById(String siteId){
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
}
