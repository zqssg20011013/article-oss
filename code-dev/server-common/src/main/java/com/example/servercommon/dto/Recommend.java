package com.example.servercommon.dto;
import com.example.servercommon.pojo.image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @project: blog
 * @ClassName: Recommend
 * @author: smallwei
 * @creat: 2023/2/15 18:13
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommend {
    private com.example.servercommon.pojo.image image;
    private String id;


}
