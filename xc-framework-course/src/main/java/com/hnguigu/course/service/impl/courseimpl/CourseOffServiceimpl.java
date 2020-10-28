package com.hnguigu.course.service.impl.courseimpl;

import com.hnguigu.course.repository.CourseOffRepository;
import com.hnguigu.course.service.course.CourseOffService;
import com.hnguigu.domain.course.CourseBase;
import com.hnguigu.domain.course.CourseMarket;
import com.hnguigu.domain.course.CourseOff;
import com.hnguigu.domain.course.CoursePic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CourseOffServiceimpl implements CourseOffService {

    @Autowired
    private CourseOffRepository courseOffRepository;

    @Override
    public boolean addCourseOff(CourseBase courseBase, CourseMarket courseMarket, Date date, CoursePic coursePic, String json) {
        CourseOff courseOff = new CourseOff();
        courseOff.setName(courseBase.getName());
        courseOff.setUsers(courseBase.getUsers());
        courseOff.setMt(courseBase.getMt());
        courseOff.setSt(courseBase.getSt());
        courseOff.setGrade(courseBase.getGrade());
        courseOff.setStudymodel(courseBase.getStudymodel());
        courseOff.setDescription(courseBase.getDescription());
        courseOff.setTimestamp(new Date());
        courseOff.setCharge(courseMarket.getCharge());
        courseOff.setValid(courseMarket.getValid());
        courseOff.setQq(courseMarket.getQq());
        courseOff.setPrice(courseMarket.getPrice());
        courseOff.setPrice_old(courseMarket.getPrice_old());
        courseOff.setTeachplan(json);
        courseOff.setExpires(date);
        if (coursePic != null) {
            courseOff.setPic(coursePic.getPic());
        }
        CourseOff save = courseOffRepository.save(courseOff);
        if (save != null) {
            return true;
        }
        return false;
    }
}
