package com.example.servercommon.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @project: blog
 * @ClassName: image
 * @author: smallwei
 * @creat: 2022/9/1 18:09
 * 描述:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class image implements Serializable {
    private int id;
    private String username;
    private String articleid;
    private String url;
}
