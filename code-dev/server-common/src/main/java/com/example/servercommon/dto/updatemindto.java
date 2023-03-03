package com.example.servercommon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @project: blog
 * @ClassName: updateDto
 * @author: smallwei
 * @creat: 2023/1/30 15:55
 * 描述:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class updatemindto  {
       @JsonProperty(value = "username")
        private    String username;
       @JsonProperty(value = "objectname")
       private    String objectname;
       @JsonProperty(value = "localpath")
       private   String localpath;
}
