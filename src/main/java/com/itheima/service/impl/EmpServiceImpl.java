package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpExpr;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class  EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;


//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//        long total = empMapper.count();
//        // 计算开始索引
//        int start = (page - 1) * pageSize;
//        List<Emp> rows = empMapper.list(start, pageSize);
//        return new PageResult<Emp>(total,rows);
//    }
/*
    pageHelper
*/
@Override
public PageResult<Emp> page(EmpQueryParam empQueryParam) {
    //1.设置分页参数
    PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
    //2.执行查询
    List<Emp> empList = empMapper.list(empQueryParam);
    //3.解析查询结果，并封装
    Page<Emp> p = (Page<Emp>) empList;
    return new PageResult<>(p.getTotal(),p.getResult());
}

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) {
    // 1.保存员工基本数据
    emp.setCreateTime(LocalDateTime.now());
    emp.setUpdateTime(LocalDateTime.now());
    empMapper.insert(emp);
    // 2.保存员工经历数据
    List<EmpExpr> empExprs = emp.getEmpExprs();
    if (!CollectionUtils.isEmpty(empExprs)){
        // 设置员工ID
        empExprs.forEach(empExpr -> {
            empExpr.setEmpId(emp.getId());
        });
        empExprMapper.insertBatch(empExprs);
    }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        // 1.删除员工基本数据
        empMapper.deleteByIds(ids);
        // 2.删除员工经历数据
        empExprMapper.deleteEmpByIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateById(Emp emp) {
        //1. 补全基础属性-updateTime
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        //2. 删除员工经历数据
        empExprMapper.deleteEmpByIds(Arrays.asList(emp.getId()));
        //3. 保存员工经历数据
        List<EmpExpr> empExprs = emp.getEmpExprs();
        if (!CollectionUtils.isEmpty(empExprs)){
            // 4. 设置员工ID
            empExprs.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(empExprs);
        }
    }

}
