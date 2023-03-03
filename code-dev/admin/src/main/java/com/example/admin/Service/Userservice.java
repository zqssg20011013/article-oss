package com.example.admin.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.PO.Admin;
import com.example.admin.PO.User;
import com.example.admin.PO.admin_type;
import com.example.admin.dto.PageDto;
import com.example.admin.dto.banDto;
import com.example.admin.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @project: blog
 * @ClassName: Userservice
 * @author: smallwei
 * @creat: 2022/8/19 13:49
 * 描述:
 */
@Service
@Transactional
public class Userservice {
    @Autowired
    UserMapper usermapper;
    @Autowired
    com.example.admin.mapper.user users;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    com.example.admin.mapper.type typer;
    public Admin login(Admin user){
        if(user.getUsername().equals(usermapper.getAdminByName(user.getUsername()).getUsername())&&user.getPassword()
                .equals(usermapper.getAdminByName(user.getUsername()).getPassword())){
            return user;
        }
        return null;
    }
    public User getUser(Admin user){
    return  users.GetuserbyUsername(user.getUsername());
    }
    public User  updset(User user){
        users.updateById(user);
        return users.GetuserbyUsername(user.getUsername());
    }
    public User getuser(String username){
       return  users.GetuserbyUsername(username);
    }
    public List<admin_type> Search(String username){
        QueryWrapper<admin_type> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",username);
        return  typer.selectList(queryWrapper);
    }
    public List<admin_type> List(){

         return typer.List();
    }
    public void type(String username){
        typer.insert(new admin_type(username,false,false));
    }
    public PageDto selectPage(long current ){
        Page<admin_type> page =new Page(current,10,true);
          Page<admin_type> page1= typer.selectPage(page,null);
          System.out.print(page1.getPages());
         PageDto dto=new PageDto(page1.getRecords(),  current, page1.getPages());

        return dto;
    }
    public void   create(Admin admin){
        usermapper.insert(admin);
        admin_type type=new admin_type(admin.getUsername(),false,false);
        typer.insert(type);
        User user=new User();
        user.setUsername(admin.getUsername());
        users.insert(user);

    }
    public void setBanned_state(banDto dto){
        System.out.print(dto.getLocktype());

      typer.updates(dto.getUsername(),dto.getLocktype());
    }
    public Boolean gettype(String username){
      return  typer.selectByUsername(username).getType();
    }
    public Boolean getlockype(String username){
        return  typer.selectByUsername(username).getLocktype();
    }



}
