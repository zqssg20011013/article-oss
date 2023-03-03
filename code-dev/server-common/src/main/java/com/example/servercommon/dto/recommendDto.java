package com.example.servercommon.dto;

import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: blog
 * @ClassName: recommendDto
 * @author: smallwei
 * @creat: 2023/2/15 14:56
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class recommendDto {
    private JSONArray jsonarray;
    private  int   totalpage;
    private int  currentpage;
}
