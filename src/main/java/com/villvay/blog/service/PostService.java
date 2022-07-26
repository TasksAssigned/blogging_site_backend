package com.villvay.blog.service;

import com.villvay.blog.dto.PostDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
public interface PostService {
    ResponseEntity getAllPosts();

    ResponseEntity getPost(Integer id);

    ResponseEntity addNewPost(PostDTO dto);

    ResponseEntity updatePost(PostDTO dto, Integer id);

    ResponseEntity deletePost(Integer id);

    ResponseEntity getPostsByAuthor(Integer id);
}
