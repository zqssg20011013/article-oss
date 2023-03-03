package com.example.admin.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: blog
 * @ClassName: admin_type
 * @author: smallwei
 * @creat: 2023/2/11 11:50
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("admin_type")
public class admin_type {
   @TableField("username")
   private String username;
   @TableField("type")
   private Boolean type;
   @TableField("locktype")
   private Boolean locktype;
}
