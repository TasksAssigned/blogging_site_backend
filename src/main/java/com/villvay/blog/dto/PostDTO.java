package com.villvay.blog.dto;

import com.villvay.blog.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
@Data
@AllArgsConstructor
public class PostDTO {
    private Integer id;
    private String title;
    private String body;
    private Integer authorId;
    private Date createdOn;
    private Date modifiedOn;
}
