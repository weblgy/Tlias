package com.itheima.service.impl;

import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.JobOption;
import com.itheima.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private EmpMapper empMapper;
    @Override
    public JobOption getEmoJobData() {
        // 1.查询员工职位数据
        List< Map<String, Object>> list = empMapper.getEmpJobData();
        log.info("员工职位数据:{}", list);
        // 2.封装员工职位数据
        List<Object> jobList = list.stream().map(m -> m.get("pos")).toList();
        List<Object> dataList = list.stream().map(m -> m.get("num")).toList();
        return new JobOption(jobList,dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        return empMapper.getEmpGenderData();
    }
}
