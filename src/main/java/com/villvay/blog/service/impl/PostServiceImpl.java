package com.villvay.blog.service.impl;

import com.villvay.blog.dto.PostDTO;
import com.villvay.blog.entity.Author;
import com.villvay.blog.entity.Post;
import com.villvay.blog.repository.AuthorRepository;
import com.villvay.blog.repository.PostRepository;
import com.villvay.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public ResponseEntity getAllPosts() {
        List<PostDTO> postDTOS = new ArrayList<>();
        postRepository.findAll().forEach(n->{
            postDTOS.add(new PostDTO(n.getId().intValue(),n.getTitle(),n.getBody(),
                    n.getAuthor().getId().intValue(),n.getCreatedOn(),n.getModifiedOn()));
        });
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getPost(Integer id) {
        Optional<Post> existingPost = postRepository.findById(id.longValue());
        if(existingPost.isPresent()){
            Post post = existingPost.get();
            return new ResponseEntity<>(new PostDTO(post.getId().intValue(),post.getTitle(),post.getBody(),
                    post.getAuthor().getId().intValue(),post.getCreatedOn(),post.getModifiedOn()), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("There is no such Post or Author", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity getPostsByAuthor(Integer id) {
        boolean exists = authorRepository.existsById(id.longValue());
        if(exists){
            List<PostDTO> postDTOS = new ArrayList<>();
            postRepository.findPostByAuthor_Id(id.longValue()).forEach(n->{
                postDTOS.add(new PostDTO(n.getId().intValue(),n.getTitle(),n.getBody(),
                        n.getAuthor().getId().intValue(),n.getCreatedOn(),n.getModifiedOn()));
            });
            return new ResponseEntity<>(postDTOS, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("There is no such Author", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity addNewPost(PostDTO dto) {

        Optional<Author> author = authorRepository.findById(dto.getAuthorId().longValue());
        if (!author.isPresent()) {
            return new ResponseEntity<>("Author Id is incorrect", HttpStatus.BAD_REQUEST);
        }
        if(author.get().isLoggedIn()==false){
            return new ResponseEntity<>("Need to Login to perform this action", HttpStatus.BAD_REQUEST);
        }
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        post.setAuthor(author.get());
        post.setCreatedOn(new Date());
        post.setModifiedOn(new Date());
        postRepository.save(post);
        return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updatePost(PostDTO dto, Integer id) {
        Optional<Post> existingPost = postRepository.findById(id.longValue());
        Optional<Author> author = authorRepository.findById(dto.getAuthorId().longValue());

        if(author.get().isLoggedIn()==false){
            return new ResponseEntity<>("Need to Login to perform this action", HttpStatus.BAD_REQUEST);
        }

        if(!existingPost.isPresent() || !author.isPresent()){
            return new ResponseEntity<>("There is no such Post or Author", HttpStatus.BAD_REQUEST);
        }

        Post post = existingPost.get();
        post.setTitle(dto.getTitle());
        post.setBody(dto.getBody());
        post.setAuthor(author.get());
        post.setCreatedOn(new Date());
        post.setModifiedOn(new Date());
        postRepository.save(post);
        return new ResponseEntity<>("Updated Successfully", HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity deletePost(Integer id) {
        Optional<Post> existingPost = postRepository.findById(id.longValue());
        if(existingPost.isPresent()){
            postRepository.deleteById(id.longValue());
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>("There is no such Post", HttpStatus.BAD_REQUEST);
        }
    }
}
