package com.villvay.blog.service;

import com.villvay.blog.dto.AuthorRegisterDTO;
import com.villvay.blog.dto.LoginDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
public interface AuthorService {

    ResponseEntity getAllAuthors();

    ResponseEntity getAuthor(Integer id);

    ResponseEntity addNewAuthor(AuthorRegisterDTO dto);

    ResponseEntity updateAuthor(AuthorRegisterDTO dto, Integer id);

    ResponseEntity deleteAuthor(Integer id);

    ResponseEntity loginAuthor(LoginDTO dto);

    ResponseEntity logoutAuthor(LoginDTO dto);
}
