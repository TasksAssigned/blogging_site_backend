package com.villvay.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-25
 **/
@Data
@AllArgsConstructor
public class LoginDTO {
    String username;
    String password;
}
