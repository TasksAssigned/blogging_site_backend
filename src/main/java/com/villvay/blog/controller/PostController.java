package com.villvay.blog.controller;

import com.villvay.blog.dto.PostDTO;
import com.villvay.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author  : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
@RestController
@RequestMapping(path = "/blog")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/post/all")
    public ResponseEntity getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping(path = "/post/{id}")
    public ResponseEntity getPost(@PathVariable Integer id){
        return postService.getPost(id);
    }

    @GetMapping(path = "/author/{id}/post")
    public ResponseEntity getPostsByAuthor(@PathVariable Integer id){
        return postService.getPostsByAuthor(id);
    }

    @PostMapping(path = "/post/add")
    public ResponseEntity addNewPost(@RequestBody PostDTO dto){
        return postService.addNewPost(dto);
    }

    @PutMapping(path = "/post/update")
    public ResponseEntity updatePost(@RequestBody PostDTO dto, @RequestParam Integer id){
        return postService.updatePost(dto,id);
    }

    @DeleteMapping(path = "/post/delete")
    public ResponseEntity deletePost(@RequestParam Integer id){
        return postService.deletePost(id);
    }
}
