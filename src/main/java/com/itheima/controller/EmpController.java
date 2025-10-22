package com.itheima.controller;


import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    /**
     * 分页查询
     *
     */
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }
    /**
     * 新增员工
     */
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("新增员工:{}", emp);
        empService.save(emp);
        return Result.success();
    }
    /**
     * 员工删除
     */
    @DeleteMapping
    public Result delete(@RequestParam List< Integer> ids){
        log.info("删除员工:{}", ids);
        empService.deleteByIds(ids);
        return Result.success();
    }
    /**
     * 员工回显
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("员工回显:{}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }
    /**
     * 员工修改
     */
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("员工修改:{}", emp);
        empService.updateById(emp);
        return Result.success();
    }
}
