package com.hnguigu.course.service.impl.courseimpl;

import com.hnguigu.common.exception.CustomException;
import com.hnguigu.common.exception.ExceptionCast;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.common.model.response.ResultCode;
import com.hnguigu.course.repository.TeachplanMediaRepositor;
import com.hnguigu.course.repository.TeachplanRepository;
import com.hnguigu.course.service.course.TeachplanService;
import com.hnguigu.domain.course.Teachplan;
import com.hnguigu.domain.course.TeachplanMedia;
import com.hnguigu.domain.course.ext.TeachplanNode;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.course.response.CourseCode;
import com.hnguigu.domain.course.response.DeleteCourseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TeachplanServiceimpl implements TeachplanService {


    @Autowired
    private TeachplanRepository teachplanRepository;
    @Autowired
    private TeachplanMediaRepositor teachplanMediaRepositor;

    @Override
    public TeachplanNode queryTeachplanBycourseid(String id) {
        Specification<Teachplan> specification = new Specification<Teachplan>() {
            @Override
            public Predicate toPredicate(Root<Teachplan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if(id!=null){
                    Predicate a1 = criteriaBuilder.equal(root.get("courseid"),id);
                    predicate =criteriaBuilder.and(a1);
                }
                return predicate;
            }
        };
        List<Teachplan> list = teachplanRepository.findAll(specification);
        /*TeachplanNode gen1 = new TeachplanNode();
        List<TeachplanNode> nodeList = new ArrayList<>();*/
        TeachplanNode gen =  new TeachplanNode();
        //查询根对象
        for (Teachplan teachplan : list) {
            if(teachplan.getParentid().equals("0")){
                gen.setId(teachplan.getId());
                gen.setPname(teachplan.getPname());
                gen.setParentid(teachplan.getParentid());
                gen.setGrade(teachplan.getGrade());
                gen.setPtype(teachplan.getPtype());
                gen.setDescription(teachplan.getDescription());
                gen.setCourseid(teachplan.getCourseid());
                gen.setStatus(teachplan.getStatus());
                gen.setOrderby(teachplan.getOrderby());
                gen.setTimelength(teachplan.getTimelength());
                gen.setTrylearn(teachplan.getTrylearn());
                gen.setChildren(new ArrayList<TeachplanNode>());
                break;
            }
        }
        //查询parentid等于跟对象的id
        List<TeachplanNode> list1 = new ArrayList<>();
        for (Teachplan teachplan : list) {
            if(teachplan.getParentid().equals(gen.getId())){
                TeachplanNode zi =  new TeachplanNode();
                zi.setId(teachplan.getId());
                zi.setPname(teachplan.getPname());
                zi.setParentid(teachplan.getParentid());
                zi.setGrade(teachplan.getGrade());
                zi.setPtype(teachplan.getPtype());
                zi.setDescription(teachplan.getDescription());
                zi.setCourseid(teachplan.getCourseid());
                zi.setStatus(teachplan.getStatus());
                zi.setOrderby(teachplan.getOrderby());
                zi.setTimelength(teachplan.getTimelength());
                zi.setTrylearn(teachplan.getTrylearn());
                zi.setChildren(new ArrayList<TeachplanNode>());
                list1.add(zi);
            }
        }
        gen.setChildren(list1);
        //查询子子节点
        List<TeachplanNode> list2 = new ArrayList<>();
        for (TeachplanNode child : gen.getChildren()) {
            for (Teachplan teachplan : list) {
                if(child.getId().equals(teachplan.getParentid())){
                    TeachplanNode zzi =  new TeachplanNode();
                    zzi.setId(teachplan.getId());
                    zzi.setPname(teachplan.getPname());
                    zzi.setParentid(teachplan.getParentid());
                    zzi.setGrade(teachplan.getGrade());
                    zzi.setPtype(teachplan.getPtype());
                    zzi.setDescription(teachplan.getDescription());
                    zzi.setCourseid(teachplan.getCourseid());
                    zzi.setStatus(teachplan.getStatus());
                    zzi.setOrderby(teachplan.getOrderby());
                    zzi.setTimelength(teachplan.getTimelength());
                    zzi.setTrylearn(teachplan.getTrylearn());
                    zzi.setChildren(new ArrayList<TeachplanNode>());
                    list2.add(zzi);
                }
            }
            child.setChildren(list2);
            // list2.clear();
            list2 = new ArrayList<TeachplanNode>();
        }
        /*nodeList.add(gen);
        gen1.setChildren(nodeList);*/
        return gen;
    }

    @Override
    public List<Teachplan> findTeachplan(String id) {
        Specification<Teachplan> specification = new Specification<Teachplan>() {
            @Override
            public Predicate toPredicate(Root<Teachplan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
                if(id!=null){
                    Predicate a1 = criteriaBuilder.equal(root.get("parentid"),id);
                    predicate =criteriaBuilder.and(a1);
                }
                return predicate;
            }
        };
        List<Teachplan> list = teachplanRepository.findAll(specification);
        return list;
    }

    @Override
    public Teachplan findTeachplanByid(String id) {
        Optional<Teachplan> teachplan = teachplanRepository.findById(id);
        Teachplan teachplan1 = null;
        if(teachplan.isPresent()){
            teachplan1 = teachplan.get();
        }
        return teachplan1;
    }

    @Override
    public AddCourseResult addTeachplan(Teachplan teachplan) {
        Teachplan base = null;
        AddCourseResult addCourseResult = null;
        if(teachplan!=null){
            if(teachplan.getParentid()==null){
                TeachplanNode teachplanNode = queryTeachplanBycourseid(teachplan.getCourseid());
                teachplan.setParentid(teachplanNode.getId());
                teachplan.setGrade("2");
            }else{
                teachplan.setGrade("3");
            }
            base = teachplanRepository.save(teachplan);
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
    public DeleteCourseResult deleteTheachplan(TeachplanNode teachplanNode) {

        DeleteCourseResult deleteCourseResult = null;
        if(teachplanNode!=null){
            String id = teachplanNode.getId();
            if(id!=null){
                Teachplan teachplan = new Teachplan();
                teachplan.setId(id);
                teachplan.setGrade("1");
                teachplanRepository.delete(teachplan);
                List<Teachplan> teachplan1 = findTeachplan(id);
                if(teachplan1.size()>0){
                    for (Teachplan teachplan2 : teachplan1) {
                        teachplanRepository.delete(teachplan2);
                    }
                }
            }
        }else{
            new CustomException(CommonCode.INVALID_PARAM);
        }
        ResultCode resultCode =null;
        deleteCourseResult = new DeleteCourseResult(CommonCode.SUCCESS,"1");
        return deleteCourseResult;
    }

    //保存课程计划和媒资w文件的管理信息
    @Override
    public ResponseResult saveMdia(TeachplanMedia teachplanMedia) {
        if(teachplanMedia==null|| StringUtils.isEmpty(teachplanMedia.getTeachplanId())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //校验课程计划是否是三级
        //课程计划
        String teachplanId = teachplanMedia.getTeachplanId();
        //查询到课程计划
        Optional<Teachplan> teachplan = teachplanRepository.findById(teachplanId);
        if(!teachplan.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //查询到教学计划
        Teachplan teachplan1 = teachplan.get();
        //取出等级
        String grade = teachplan1.getGrade();
        if(StringUtils.isEmpty(grade)){
               ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_GRADEERROR);
        }
        //查询teachplanMedia
        Optional<TeachplanMedia> optionalTeachplanMedia = teachplanMediaRepositor.findById(teachplanId);
        TeachplanMedia teachplanMedia1=null;
        if(optionalTeachplanMedia.isPresent()){
             teachplanMedia1=optionalTeachplanMedia.get();
        }else{
            teachplanMedia1=new TeachplanMedia();
        }
        //将TeachplanMedia保存到数据库
        teachplanMedia1.setCourseId(teachplan1.getCourseid());
        teachplanMedia1.setMediaId(teachplanMedia.getMediaId());
        teachplanMedia1.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
        teachplanMedia1.setMediaUrl(teachplanMedia.getMediaUrl());
        teachplanMedia1.setTeachplanId(teachplanId);
        teachplanMediaRepositor.save(teachplanMedia1);

        return new ResponseResult(CommonCode.SUCCESS);
    }
}
