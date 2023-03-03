package com.example.servercommon.dto;

import com.example.servercommon.pojo.article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @project: blog
 * @ClassName: view
 * @author: smallwei
 * @creat: 2023/2/12 20:56
 * 描述:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagedto {
    List<article> articleList;
     int currentpage;
     int totalpage;

}
