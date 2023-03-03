package com.example.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: blog
 * @ClassName: loginDto
 * @author: smallwei
 * @creat: 2023/2/17 0:49
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class loginDto {
    private String username;
    private String password;
}
