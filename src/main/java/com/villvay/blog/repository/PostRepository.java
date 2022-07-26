package com.villvay.blog.repository;

import com.villvay.blog.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findPostByAuthor_Id(Long authorId);
}
