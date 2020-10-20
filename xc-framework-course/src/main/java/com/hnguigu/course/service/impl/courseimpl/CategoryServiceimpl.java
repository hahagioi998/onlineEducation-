package com.hnguigu.course.service.impl.courseimpl;

import com.hnguigu.course.repository.CategoryRepository;
import com.hnguigu.course.service.course.CategoryService;
import com.hnguigu.domain.course.Category;
import com.hnguigu.domain.course.ext.CategoryNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceimpl implements CategoryService {

    @Autowired
    private  CategoryRepository categoryRepository;

    @Override
    public CategoryNode findList() {
        List<Category> list = categoryRepository.findAll();
        CategoryNode gen =  new CategoryNode();
        //查询根对象
        for (Category category : list) {
            if(category.getParentid().equals("0")){
                gen.setId(category.getId());
                gen.setName(category.getName());
                gen.setLabel(category.getLabel());
                gen.setParentid(category.getParentid());
                gen.setIsshow(category.getIsshow());
                gen.setOrderby(category.getOrderby());
                gen.setIsleaf(category.getIsleaf());
                gen.setChildren(new ArrayList<CategoryNode>());
                break;
            }
        }

        //查询parentid等于跟对象的id
        List<CategoryNode> list1 = new ArrayList<>();
        for (Category category : list) {
            if(category.getParentid().equals(gen.getId())){
                CategoryNode zi =  new CategoryNode();
                zi.setId(category.getId());
                zi.setName(category.getName());
                zi.setLabel(category.getLabel());
                zi.setParentid(category.getParentid());
                zi.setIsshow(category.getIsshow());
                zi.setOrderby(category.getOrderby());
                zi.setIsleaf(category.getIsleaf());
                zi.setChildren(new ArrayList<CategoryNode>());
//                gen.getChildren().add(zi);
                list1.add(zi);
            }
        }
        gen.setChildren(list1);
        List<CategoryNode> list2 = new ArrayList<>();
        for (CategoryNode child : gen.getChildren()) {

            for (Category category : list) {
                if(child.getId().equals(category.getParentid())){
                    CategoryNode zzi = new CategoryNode();
                    zzi.setId(category.getId());
                    zzi.setName(category.getName());
                    zzi.setLabel(category.getLabel());
                    zzi.setParentid(category.getParentid());
                    zzi.setIsshow(category.getIsshow());
                    zzi.setOrderby(category.getOrderby());
                    zzi.setIsleaf(category.getIsleaf());
                    list2.add(zzi);
                }
            }
            child.setChildren(list2);
           // list2.clear();
            list2 = new ArrayList<CategoryNode>();
        }
        return gen;
    }
}
