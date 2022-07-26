package com.villvay.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
@Data
@AllArgsConstructor
public class CommentDTO {
    private Integer id;
    private Integer postId;
    private String name;
    private String email;
    private String body;
    private Date createdOn;
    private Date modifiedOn;
}
