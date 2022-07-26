package com.villvay.blog.repository;

import com.villvay.blog.entity.Comment;
import com.villvay.blog.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findCommentsByPostId(Long id);
    List<Comment> findCommentsByPost_Author_Id(Long id);
}
