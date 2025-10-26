package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public Long count();
//
//    /**
//     *
//     * @param start
//     * @param pageSize
//     * @return
//     */
//    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id " +
//            "order by e.update_time desc limit #{start},#{pageSize}" )
//    public List<Emp> list(Integer start, Integer pageSize);

public List<Emp> list(EmpQueryParam empQueryParam);
@Options(useGeneratedKeys = true, keyProperty = "id")
@Insert("insert into emp(username,name,gender,phone,job,salary,image,entry_date,dept_id,create_time,update_time) values " +
        "(#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})") // 补充右括号
void insert(Emp emp);

void deleteByIds(List<Integer> ids);

Emp getById(Integer id);

void updateById(Emp emp);

@MapKey("pos")
List<Map<String, Object>> getEmpJobData();

@MapKey("name")
List<Map<String, Object>> getEmpGenderData();

@Select("select * from emp where username=#{username} and password=#{password}")
Emp getByUsername(Emp emp);
}
