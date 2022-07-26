package com.villvay.blog.service.impl;

import com.villvay.blog.dto.CommentDTO;
import com.villvay.blog.entity.Comment;
import com.villvay.blog.entity.Post;
import com.villvay.blog.repository.AuthorRepository;
import com.villvay.blog.repository.CommentRepository;
import com.villvay.blog.repository.PostRepository;
import com.villvay.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public ResponseEntity getAllComments() {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        commentRepository.findAll().forEach(n->{
            commentDTOS.add(new CommentDTO(n.getId().intValue(),n.getPost().getId().intValue(),n.getName(),n.getEmail(),
                    n.getBody(),n.getCreateOn(),n.getModifiedOn()));
        });
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getComment(Integer id) {
        Optional<Comment> existingComment = commentRepository.findById(id.longValue());
        if(existingComment.isPresent()){
            Comment comment = existingComment.get();
            return new ResponseEntity<>(new CommentDTO(comment.getId().intValue(),comment.getPost().getId().intValue(),comment.getName(),
                    comment.getEmail(),comment.getBody(),comment.getCreateOn(),comment.getModifiedOn()), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("There is no such Comment or Post", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity getCommentsByPost(Integer id) {
        boolean exists = postRepository.existsById(id.longValue());
        if(exists){
            List<CommentDTO> commentDTOS = new ArrayList<>();
            commentRepository.findCommentsByPostId(id.longValue()).forEach(n->{
                commentDTOS.add(new CommentDTO(n.getId().intValue(),n.getPost().getId().intValue(),n.getName(),n.getEmail(),
                        n.getBody(),n.getCreateOn(),n.getModifiedOn()));
            });
            return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("There is no such Post", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity getCommentsByAuthor(Integer id) {
        boolean exists = authorRepository.existsById(id.longValue());
        if (exists) {
            List<CommentDTO> commentDTOS = new ArrayList<>();
            commentRepository.findCommentsByPost_Author_Id(id.longValue()).forEach(n->{
                commentDTOS.add(new CommentDTO(n.getId().intValue(),n.getPost().getId().intValue(),n.getName(),n.getEmail(),
                        n.getBody(),n.getCreateOn(),n.getModifiedOn()));
            });
            return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("There is no such Post", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity addNewComment(CommentDTO dto) {

        Optional<Post> post = postRepository.findById(dto.getPostId().longValue());
        if(post.isPresent()){
            Comment comment = new Comment();
            comment.setPost(post.get());
            comment.setName(dto.getName());
            comment.setEmail(dto.getEmail());
            comment.setBody(dto.getBody());
            comment.setCreateOn(dto.getCreatedOn());
            comment.setModifiedOn(dto.getModifiedOn());
            commentRepository.save(comment);
            return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("POST Id is incorrect", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity updateComment(CommentDTO dto, Integer id) {
        Optional<Post> post = postRepository.findById(dto.getPostId().longValue());
        Optional<Comment> existingComment = commentRepository.findById(id.longValue());
        if(post.isPresent() && existingComment.isPresent()){
            Comment comment = existingComment.get();
            comment.setPost(post.get());
            comment.setName(dto.getName());
            comment.setEmail(dto.getEmail());
            comment.setBody(dto.getBody());
            comment.setCreateOn(dto.getCreatedOn());
            comment.setModifiedOn(dto.getModifiedOn());
            commentRepository.save(comment);
            return new ResponseEntity<>("Updated Successfully", HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>("There is no such Comment or Post", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity deleteComment(Integer id) {
        Optional<Comment> existingComment = commentRepository.findById(id.longValue());
        if(existingComment.isPresent()){
            commentRepository.deleteById(id.longValue());
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>("There is no such Comment", HttpStatus.BAD_REQUEST);
        }
    }
}
