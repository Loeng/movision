package com.zhuhuibao.mybatis.oms.service;

/**
 * Created by cxx on 2016/3/14 0014.
 */

import com.zhuhuibao.common.ResultBean;
import com.zhuhuibao.common.SysBean;
import com.zhuhuibao.mybatis.oms.entity.Category;
import com.zhuhuibao.mybatis.oms.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类目管理
 * @author cxx
 *
 */
@Service
@Transactional
public class CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 查询品牌所属大系统
     */
    public List<ResultBean> findSystemByBrand(String id){
        log.debug("查询品牌所属大系统");
        List<ResultBean> resultBeanList = categoryMapper.findSystemByBrand(id);
        return resultBeanList;
    }

    /**
     * 查询品牌所属大系统,子系统
     */
    public List<SysBean> findCategoryByBrand(String id){
        log.debug("查询品牌所属大系统,子系统");
        List<SysBean> sysBeanList = categoryMapper.findCategoryByBrand(id);
        return sysBeanList;
    }

    /**
     * 查询所有大系统
     */
    public List<ResultBean> findSystemList(){
        log.debug("查询所有大系统");
        List<ResultBean> sysList = categoryMapper.findSystemList();
        return sysList;
    }

    /**
     * 查询所有大系统,子系统
     */
    public List<SysBean> searchAll(){
        log.debug("查询所有大系统,子系统");
        List<SysBean> allList = categoryMapper.searchAll();
        return allList;
    }

    /**
     * 根据大系统id查询子系统
     */
    public List<ResultBean> findSubSystemList(String id){
        log.debug("根据大系统id查询子系统");
        List<ResultBean> SubSystemList = categoryMapper.findSubSystemList(id);
        return SubSystemList;
    }

    /**
     * 根据大系统id查询大系统信息
     */
    public Category findSystem(String id){
        log.debug("根据大系统id查询大系统信息");
        Category category = categoryMapper.findSystem(id);
        return category;
    }


    /**
     * 添加类目
     */
    public int addSystem(Category category){
        log.debug("添加类目");
        int isAdd = categoryMapper.addSystem(category);
        return isAdd;
    }

    /**
     * 更新类目
     */
    public int updateSystem(Category category){
        log.debug("更新类目");
        int isUpdate = categoryMapper.updateSystem(category);
        return isUpdate;
    }

    /**
     * 删除类目
     */
    public int deleteSystem(Category category){
        log.debug("删除类目");
        int isDelete = categoryMapper.deleteSystem(category);
        return isDelete;
    }
}
