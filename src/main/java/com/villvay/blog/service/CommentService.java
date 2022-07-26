package com.villvay.blog.service;

import com.villvay.blog.dto.CommentDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
public interface CommentService {
    ResponseEntity getAllComments();

    ResponseEntity getComment(Integer id);

    ResponseEntity addNewComment(CommentDTO dto);

    ResponseEntity updateComment(CommentDTO dto, Integer id);

    ResponseEntity deleteComment(Integer id);

    ResponseEntity getCommentsByPost(Integer id);

    ResponseEntity getCommentsByAuthor(Integer id);
}
