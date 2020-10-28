package com.hnguigu.course.service.impl.courseimpl;

import com.hnguigu.common.exception.CustomException;
import com.hnguigu.common.exception.ExceptionCast;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResultCode;
import com.hnguigu.course.client.CmsPageClient;
import com.hnguigu.course.repository.CourseBaseRepository;
import com.hnguigu.course.repository.CourseMarketRepository;
import com.hnguigu.course.repository.CoursePicRepository;
import com.hnguigu.course.repository.TeachplanRepository;
import com.hnguigu.course.service.course.CourseBaseService;
import com.hnguigu.course.service.course.TeachplanService;
import com.hnguigu.domain.cms.CmsPage;
import com.hnguigu.domain.cms.response.CmsPageResult;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.CourseMarket;
import com.hnguigu.domain.course.CoursePic;
import com.hnguigu.domain.course.Teachplan;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.ext.CourseView;
import com.hnguigu.domain.course.ext.TeachplanNode;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.course.response.CourseCode;
import com.hnguigu.domain.course.response.CoursePublishResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseBaseServiceimpl implements CourseBaseService {

    @Autowired
    private CourseBaseRepository courseBaseRepository;

    @Autowired
    private CoursePicRepository coursePicRepository;

    @Autowired
    private TeachplanRepository teachplanRepository;
    @Autowired
    CourseMarketRepository courseMarketRepository;
    @Autowired
    TeachplanService teachplanService;
    @Autowired
    CmsPageClient cmsPageClient;
    @Value("${course‐publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course‐publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course‐publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course‐publish.siteId}")
    private String publish_siteId;
    @Value("${course‐publish.templateId}")
    private String publish_templateId;
    @Value("${course‐publish.previewUrl}")
    private String previewUrl;
    @Override
    public QueryResult<CourseInfo> queryPageCourseBase(Integer page, Integer size,String userId) {
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Specification<CourseBase> specification1 = new Specification<CourseBase>() {
            @Override
            public Predicate toPredicate(Root<CourseBase> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if(userId!=null){
                    Predicate a1 = criteriaBuilder.equal(root.get("userId"),userId);
                    predicate =criteriaBuilder.and(a1);
                }
                return predicate;
            }
        };
        Page<CourseBase> page1 = courseBaseRepository.findAll(specification1,pageRequest);
        List<CourseBase> queryResult1 = page1.getContent();
        List<CourseInfo> list = new ArrayList<>();
        for (CourseBase courseBase : queryResult1) {
            String id = courseBase.getId();
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setId(id);
            courseInfo.setName(courseBase.getName());
            courseInfo.setUsers(courseBase.getUsers());
            courseInfo.setMt(courseBase.getMt());
            courseInfo.setSt(courseBase.getSt());
            courseInfo.setGrade(courseBase.getGrade());
            courseInfo.setStudymodel(courseBase.getStudymodel());
            courseInfo.setTeachmode(courseBase.getTeachmode());
            courseInfo.setDescription(courseBase.getDescription());
            courseInfo.setStatus(courseBase.getStatus());
            courseInfo.setCompanyId(courseBase.getCompanyId());
            courseInfo.setUserId(courseBase.getUserId());
            Specification<CoursePic> specification = new Specification<CoursePic>() {
                @Override
                public Predicate toPredicate(Root<CoursePic> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Predicate predicate = null;
                    if(id!=null){
                        Predicate a1 = criteriaBuilder.equal(root.get("courseid"),id);
                        predicate =criteriaBuilder.and(a1);
                    }
                    return predicate;
                }
            };
            if(specification!=null){
                Optional<CoursePic> optional = coursePicRepository.findOne(specification);
                if(optional.isPresent()){
                    CoursePic coursePic = optional.get();
                    courseInfo.setPic(coursePic.getPic());
                }
            }
            list.add(courseInfo);
        }
        QueryResult<CourseInfo> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(page1.getTotalElements());
        return queryResult;
    }

//    判断传进来的数据不能为空否则抛异常
    // 如果可以添加要判断是否添加成功

    @Override
    public AddCourseResult addCourseBase(CourseBase courseBase) {
        CourseBase base = null;
        AddCourseResult addCourseResult = null;
        if(courseBase!=null){
            base = courseBaseRepository.save(courseBase);
            //添加一个根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setPname(base.getName());
            teachplan.setGrade("1");
            teachplan.setParentid("0");
            teachplan.setStatus("1");
            teachplan.setCourseid(base.getId());
            teachplanRepository.save(teachplan);
        }else{
            new CustomException(CommonCode.INVALID_PARAM);
        }
        ResultCode resultCode =null;
        if(base!=null){
            addCourseResult = new AddCourseResult(CommonCode.SUCCESS,base.getId());
        }else{
            addCourseResult = new AddCourseResult(CommonCode.FAIL,null);
        }
        return addCourseResult;
    }

    @Override
    public CourseBase queryCourseBaseByid(String id) {
        Specification<CourseBase> specification = new Specification<CourseBase>() {
            @Override
            public Predicate toPredicate(Root<CourseBase> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if(id!=null){
                    Predicate a1 = criteriaBuilder.equal(root.get("id"),id);
                    predicate =criteriaBuilder.and(a1);
                }
                return predicate;
            }
        };
        Optional<CourseBase> courseBase = courseBaseRepository.findOne(specification);
        CourseBase courseBase1  = courseBase.get();
        return courseBase1;
    }

    @Override
    public AddCourseResult updateCourseBase(CourseBase courseBase) {
        CourseBase base = null;
        AddCourseResult addCourseResult = null;
        if(courseBase!=null){
            base = courseBaseRepository.save(courseBase);
            //添加一个根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setPname(base.getName());
            teachplan.setGrade("1");
            teachplan.setParentid("0");
            teachplan.setStatus("1");
            teachplan.setCourseid(base.getId());
            teachplanRepository.save(teachplan);
        }else{
            new CustomException(CommonCode.INVALID_PARAM);
        }
        ResultCode resultCode =null;
        if(base!=null){
            addCourseResult = new AddCourseResult(CommonCode.SUCCESS,base.getId());
        }else{
            addCourseResult = new AddCourseResult(CommonCode.FAIL,null);
        }
        return addCourseResult;
    }

    //课程视图查询
    public CourseView getCoruseView(String id) {
        CourseView courseView = new CourseView();
     //查询课程基本信息
        Optional<CourseBase> optional = courseBaseRepository.findById(id);
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            courseView.setCourseBase(courseBase);
        }
    //查询课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(id);
        if(courseMarketOptional.isPresent()){
            CourseMarket courseMarket = courseMarketOptional.get();
            courseView.setCourseMarket(courseMarket);
        }
    //查询课程图片信息
        Optional<CoursePic> picOptional = coursePicRepository.findById(id);
        if(picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            courseView.setCoursePic(picOptional.get());
        }
    //查询课程计划信息
        TeachplanNode teachplanNode = teachplanService.queryTeachplanBycourseid(id);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }

    //根据id查询课程基本信息
    public CourseBase findCourseBaseById(String courseId){
        Optional<CourseBase> baseOptional = courseBaseRepository.findById(courseId);
        if(baseOptional.isPresent()) {
            CourseBase courseBase = baseOptional.get();
            return courseBase;
        }
        ExceptionCast.cast(CourseCode.COURSE_GET_NOTEXISTS);
        return null;
    }

    //课程预览
    @Override
    public CoursePublishResult preview(String id) {
        //查询课程的信息
        CourseBase courseBaseById = this.findCourseBaseById(id);
        //请求cms添加页面
        //准备CmsPage
        CmsPage cmsPage=new CmsPage();
        //站点
        cmsPage.setSiteId(publish_siteId);//课程预览站点
        //模板
        cmsPage.setTemplateId(publish_templateId);
        //页面名称
        cmsPage.setPageName(id+".html");
        //页面别名
        cmsPage.setPageAliase(courseBaseById.getName());
        //页面访问路径
        cmsPage.setPageWebPath(publish_page_webpath);
        //页面存储路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);
        //数据url
        cmsPage.setDataUrl(publish_dataUrlPre+id);
        //页面id
        cmsPage.setPageId(id);
        //远程调用cms的服务
        CmsPageResult save = cmsPageClient.save(cmsPage);
        if(!save.isSuccess()){
             //抛出异常
            return new CoursePublishResult(CommonCode.FAIL,null);
        }
        CmsPage cmsPage1 = save.getCmsPage();
        String pageId = cmsPage1.getPageId();
        //拼装页面预览的url
          String previewUrl2=previewUrl+pageId;
        System.out.println(previewUrl2);
        //f返回coursePublishResult对象(当中包含页面预览的url)
        return new CoursePublishResult(CommonCode.SUCCESS,previewUrl2);
    }
}
