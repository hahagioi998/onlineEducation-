package com.hnguigu.course.service.impl.courseimpl;

import com.hnguigu.common.exception.CustomException;
import com.hnguigu.common.model.response.CommonCode;
import com.hnguigu.common.model.response.ResultCode;
import com.hnguigu.course.repository.TeachplanRepository;
import com.hnguigu.course.service.course.TeachplanService;
import com.hnguigu.domain.course.Teachplan;
import com.hnguigu.domain.course.ext.TeachplanNode;
import com.hnguigu.domain.course.response.AddCourseResult;
import com.hnguigu.domain.course.response.DeleteCourseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeachplanServiceimpl implements TeachplanService {


    @Autowired
    private TeachplanRepository teachplanRepository;

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
        TeachplanNode gen1 = new TeachplanNode();
        List<TeachplanNode> nodeList = new ArrayList<>();
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
        nodeList.add(gen);
        gen1.setChildren(nodeList);
        return gen1;
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
    public AddCourseResult addTeachplan(Teachplan teachplan) {
        Teachplan base = null;
        AddCourseResult addCourseResult = null;
        if(teachplan!=null){
            if(teachplan.getParentid()==null){
                TeachplanNode teachplanNode = queryTeachplanBycourseid(teachplan.getCourseid());
                List<TeachplanNode> children = teachplanNode.getChildren();
                TeachplanNode teachplanNode1 = children.get(0);
                teachplan.setParentid(teachplanNode1.getId());
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
}
