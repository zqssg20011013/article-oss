package com.example.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

/**
 * @project: blog
 * @ClassName: banDTO
 * @author: smallwei
 * @creat: 2023/2/13 16:24
 * 描述:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class banDto {
    String username;

    Boolean locktype;
}
