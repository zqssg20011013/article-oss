package com.example.servercommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: blog
 * @ClassName: Url
 * @author: smallwei
 * @creat: 2023/2/16 1:22
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Url {
    private String url;
    private String articleid;
}
