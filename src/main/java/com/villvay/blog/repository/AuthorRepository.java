package com.villvay.blog.repository;

import com.villvay.blog.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
@Repository
public interface AuthorRepository extends CrudRepository<Author,Long> {
    boolean existsAuthorByUsername(String username);
    boolean existsAuthorByEmail(String email);
    Author findAuthorByUsernameAndPassword(String username, String password);
    Author findByUsername(String username);
}
