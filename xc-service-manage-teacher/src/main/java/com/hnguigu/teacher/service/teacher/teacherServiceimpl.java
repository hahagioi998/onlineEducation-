package com.hnguigu.teacher.service.teacher;

import com.hnguigu.common.exception.CustomException;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.common.model.response.ResultCode;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.Teachplan;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.ucenter.XcTeacher;
import com.hnguigu.teacher.repository.teacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Service
public class teacherServiceimpl implements teacherService {

    @Autowired
    private teacherRepository teacherRepository;

    @Override
    public XcTeacher findTeacherByuserid(String id) {

        Specification<XcTeacher> specification = new Specification<XcTeacher>() {
            @Override
            public Predicate toPredicate(Root<XcTeacher> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if(id!=null){
                    Predicate a1 = criteriaBuilder.equal(root.get("userId"),id);
                    predicate =criteriaBuilder.and(a1);
                }
                return predicate;
            }
        };

        Optional<XcTeacher> one = teacherRepository.findOne(specification);
        XcTeacher xcTeacher = null;
        if(one.isPresent()){
            xcTeacher = one.get();
        }
        return xcTeacher;
    }

    @Override
    public ResponseResult addandUpdate(XcTeacher xcTeacher) {
        XcTeacher base = null;
        ResponseResult responseResult = null;
        if(xcTeacher!=null){
            base = teacherRepository.save(xcTeacher);
        }else{
            new CustomException(CommonCode.INVALID_PARAM);
        }
        ResultCode resultCode =null;
        if(base!=null){
            responseResult = new ResponseResult(CommonCode.SUCCESS);
        }else{
            responseResult = new ResponseResult(CommonCode.FAIL);
        }
        return responseResult;
    }
}
