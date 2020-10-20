package com.hnguigu.course.service.impl.courseimpl;

import com.hnguigu.common.exception.CustomException;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.QueryResult;
import com.hnguigu.common.model.response.ResultCode;
import com.hnguigu.course.repository.CourseBaseRepository;
import com.hnguigu.course.repository.CoursePicRepository;
import com.hnguigu.course.repository.TeachplanRepository;
import com.hnguigu.course.service.course.CourseBaseService;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.CoursePic;
import com.hnguigu.domain.course.Teachplan;
import com.hnguigu.domain.course.ext.CourseInfo;
import com.hnguigu.domain.course.response.AddCourseResult;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public QueryResult<CourseInfo> queryPageCourseBase(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<CourseBase> page1 = courseBaseRepository.findAll(pageRequest);
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
}
