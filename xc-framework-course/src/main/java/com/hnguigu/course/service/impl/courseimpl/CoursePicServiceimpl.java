package com.hnguigu.course.service.impl.courseimpl;

import com.hnguigu.course.repository.CoursePicRepository;
import com.hnguigu.course.service.course.CoursePicService;
import com.hnguigu.domain.course.CoursePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Service
public class CoursePicServiceimpl implements CoursePicService {


    @Autowired
    private CoursePicRepository coursePicRepository;
    @Override
    public CoursePic findCoursePicBycourseId(String id) {
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
        Optional<CoursePic> one = coursePicRepository.findOne(specification);
        CoursePic coursePic = null;
        if(one!=null){
            coursePic = one.get();
        }
        return coursePic;
    }
}
