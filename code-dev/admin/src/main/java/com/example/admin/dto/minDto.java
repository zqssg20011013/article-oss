package com.example.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: blog
 * @ClassName: minDto
 * @author: smallwei
 * @creat: 2023/1/30 19:57
 * 描述:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class minDto {
    private String username;
    private  Boolean isdir;


}
