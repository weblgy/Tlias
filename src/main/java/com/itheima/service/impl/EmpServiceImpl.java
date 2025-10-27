package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
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
    List<EmpExpr> empExprs = emp.getExprList();
        log.info("保存员工经历数据:{}", empExprs);
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
        List<EmpExpr> empExprs = emp.getExprList();
        if (!CollectionUtils.isEmpty(empExprs)){
            // 4. 设置员工ID
            empExprs.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(empExprs);
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        Emp e = empMapper.getByUsername(emp);
        if (e != null){
            //生成 token
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",e.getId());
            claims.put("username",e.getUsername());
            String token = JwtUtils.generateJwt(claims);
           return new LoginInfo(e.getId(),e.getUsername(),e.getPassword(),e.getName(),token);
        }
        return null;

    }

}
