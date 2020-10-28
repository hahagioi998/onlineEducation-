package com.hnguigu.course.service.impl.courseimpl;

import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.course.repository.CoursePicRepository;
import com.hnguigu.course.repository.FilesystemRepositor;
import com.hnguigu.course.service.course.CoursePicService;
import com.hnguigu.domain.course.CoursePic;
import com.hnguigu.domain.filesystem.FileSystem;
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

    @Autowired
    private FilesystemRepositor filesystemRepositor;

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
        if(one.isPresent()){
            coursePic = one.get();
        }
        return coursePic;
    }

    @Override
    public ResponseResult addCoursepic(String courseId, String pic) {
        CoursePic coursePic = new CoursePic();
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        coursePicRepository.save(coursePic);
        ResponseResult responseResult = new ResponseResult(CommonCode.SUCCESS);
        return responseResult;
    }

    @Override
    public ResponseResult deleteCoursePic(String courseId) {
        CoursePic coursePic = findCoursePicBycourseId(courseId);
        String pic = coursePic.getPic();
        Optional<FileSystem> fileSystem = filesystemRepositor.findById(pic);
        if(fileSystem.isPresent()){
            FileSystem fileSystem1 = fileSystem.get();
            filesystemRepositor.delete(fileSystem1);
        }
        coursePicRepository.delete(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
