package com.hnguigu.course.service.impl.courseimpl;

import com.hnguigu.course.service.course.CourseMarketService;
import com.hnguigu.common.exception.CustomException;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.common.model.response.ResultCode;
import com.hnguigu.course.repository.CourseMarketRepository;
import com.hnguigu.domain.course.CourseMarket;
import com.hnguigu.domain.course.response.AddCourseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Service
public class CourseMarketServiceimpl implements CourseMarketService {

    @Autowired
    private CourseMarketRepository courseMarketRepository;

    @Override
    public CourseMarket findByid(String id) {
        Specification<CourseMarket> specification = new Specification<CourseMarket>() {
            @Override
            public Predicate toPredicate(Root<CourseMarket> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if(id!=null){
                    Predicate a1 = criteriaBuilder.equal(root.get("id"),id);
                    predicate =criteriaBuilder.and(a1);
                }
                return predicate;
            }
        };
        Optional<CourseMarket> one = courseMarketRepository.findOne(specification);
        CourseMarket courseMarket = null;
        if(one.isPresent()){
            courseMarket = one.get();
        }
        return courseMarket;
    }

    @Override
    public ResponseResult addAndupdate(CourseMarket courseMarket){
        CourseMarket market = null;
        AddCourseResult addCourseResult = null;
        if(courseMarket!=null){
                String id = courseMarket.getId();
                CourseMarket courseMarket1 = findByid(id);
                if(courseMarket1!=null){
                    courseMarket.setPrice_old(courseMarket1.getPrice());
                }
                market = courseMarketRepository.save(courseMarket);
        }else{
            new CustomException(CommonCode.INVALID_PARAM);
        }
        ResultCode resultCode =null;
        if(market!=null){
            addCourseResult = new AddCourseResult(CommonCode.SUCCESS,market.getId());
        }else{
            addCourseResult = new AddCourseResult(CommonCode.FAIL,null);
        }
        return addCourseResult;
    }
}
