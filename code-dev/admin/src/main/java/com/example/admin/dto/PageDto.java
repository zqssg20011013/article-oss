package com.example.admin.dto;

import com.example.admin.PO.admin_type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @project: blog
 * @ClassName: PageDto
 * @author: smallwei
 * @creat: 2023/2/12 21:13
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    List<admin_type> typeList;
    long currentpage;
     long totalpage;
}
