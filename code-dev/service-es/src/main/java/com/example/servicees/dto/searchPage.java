package com.example.servicees.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project: blog
 * @ClassName: searchPage
 * @author: smallwei
 * @creat: 2023/2/14 19:51
 * 描述:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class searchPage {
  private   String title;
   private String type;
   private String currentpage;
}
