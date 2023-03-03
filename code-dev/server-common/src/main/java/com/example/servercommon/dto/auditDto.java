package com.example.servercommon.dto;

import com.alibaba.fastjson.JSONArray;
import com.example.servercommon.pojo.image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: blog
 * @ClassName: auditDto
 * @author: smallwei
 * @creat: 2023/2/15 19:40
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class auditDto {
    private JSONArray jsonarray;
    private  int   totalpage;
    private int  currentpage;
}
