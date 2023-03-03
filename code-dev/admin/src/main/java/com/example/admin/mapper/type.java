package com.example.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.admin.PO.admin_type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @project: blog
 * @ClassName: type
 * @author: smallwei
 * @creat: 2023/2/11 12:07
 * 描述:
 */
@Mapper
public interface type extends BaseMapper<admin_type> {
    @Select("select * from admin_type where username=#{username}")
    admin_type  selectByUsername(String username);
    @Select("select * from admin_type ")
    List<admin_type> List();
    @Update("update admin_type set locktype=#{locktype} where(username=#{username})")
    void updates(@Param("username") String username,@Param("locktype") Boolean locktype);
}
