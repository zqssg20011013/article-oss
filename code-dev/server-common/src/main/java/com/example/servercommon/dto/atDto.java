package com.example.servercommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: blog
 * @ClassName: atDto
 * @author: smallwei
 * @creat: 2023/2/16 3:12
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class atDto {
    private String id;
    private int currentpage;
    private int pagesize;
}
