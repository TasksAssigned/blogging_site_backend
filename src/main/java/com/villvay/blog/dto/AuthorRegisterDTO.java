package com.villvay.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
@Data
@AllArgsConstructor
public class AuthorRegisterDTO {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String address;
    private String password;// Assume this data send from FE as encrypted one and that saves in the database as also encrypted password.
    // When logging check encrypted password from DB and FE by decrypting.
}
