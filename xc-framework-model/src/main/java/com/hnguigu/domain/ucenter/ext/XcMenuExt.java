package com.hnguigu.domain.ucenter.ext;

import com.hnguigu.domain.course.ext.CategoryNode;
import com.hnguigu.domain.ucenter.XcMenu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/3/20.
 */
@Data
@ToString
public class XcMenuExt extends XcMenu {

    List<CategoryNode> children;
}
