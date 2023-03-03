package com.example.servercommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: blog
 * @ClassName: result
 * @author: smallwei
 * @creat: 2022/8/15 20:30
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class result {
    private Integer state;
    private String Exception;
}
