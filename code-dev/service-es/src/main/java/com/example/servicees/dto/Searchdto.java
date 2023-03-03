package com.example.servicees.dto;

import com.alibaba.nacos.shaded.org.checkerframework.checker.guieffect.qual.SafeEffect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @project: blog
 * @ClassName: Searchdto
 * @author: smallwei
 * @creat: 2023/2/14 16:26
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Searchdto implements Serializable {
    private String title;
            private String type;
}
