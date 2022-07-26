package com.villvay.blog.controller;

import com.villvay.blog.dto.CommentDTO;
import com.villvay.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
@RestController
@RequestMapping(path = "/blog")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/comment/all")
    public ResponseEntity getAllComments(){
        return commentService.getAllComments();
    }

    @GetMapping(path = "/comment/{id}")
    public ResponseEntity getComment(@PathVariable Integer id){
        return commentService.getComment(id);
    }

    @GetMapping(path = "post/{id}/comment")
    public ResponseEntity getCommentsByPost(@PathVariable Integer id){
        return commentService.getCommentsByPost(id);
    }

    @GetMapping(path = "author/{id}/comment")
    public ResponseEntity getCommentsByAuthor(@PathVariable Integer id){
        return commentService.getCommentsByAuthor(id);
    }

    @PostMapping(path = "/comment/add")
    public ResponseEntity addNewComment(@RequestBody CommentDTO dto){
        return commentService.addNewComment(dto);
    }

    @PutMapping(path = "/comment/update")
    public ResponseEntity updateComment(@RequestBody CommentDTO dto, @RequestParam Integer id){
        return commentService.updateComment(dto,id);
    }

    @DeleteMapping(path = "/comment/delete")
    public ResponseEntity deleteComment(@RequestParam Integer id){
        return commentService.deleteComment(id);
    }
}
