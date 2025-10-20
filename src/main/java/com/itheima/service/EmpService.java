package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public interface EmpService {
    /*
    分页查询
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    void save(Emp emp);
}
