package com.villvay.blog.controller;

import com.villvay.blog.dto.AuthorRegisterDTO;
import com.villvay.blog.dto.LoginDTO;
import com.villvay.blog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
@RestController
@RequestMapping(path = "/blog")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/author/all")
    public ResponseEntity getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping(path = "/author/{id}")
    public ResponseEntity getAuthor(@PathVariable Integer id){
        return authorService.getAuthor(id);
    }

    @PostMapping(path = "/author/register")
    public ResponseEntity addNewAuthor(@Valid @RequestBody AuthorRegisterDTO dto){
        return authorService.addNewAuthor(dto);
    }

    @PutMapping(path = "/author/update")
    public ResponseEntity updateAuthor(@RequestBody AuthorRegisterDTO dto, @RequestParam Integer id){
        return authorService.updateAuthor(dto,id);
    }

    @DeleteMapping(path = "/author/delete")
    public ResponseEntity deleteAuthor(@RequestParam Integer id){
        return authorService.deleteAuthor(id);
    }

    @PostMapping("/author/login")
    public ResponseEntity loginAuthor(@RequestBody LoginDTO dto){
        return authorService.loginAuthor(dto);
    }

    @PostMapping("/author/logout")
    public ResponseEntity logAuthorOut(@RequestBody LoginDTO dto){
        return authorService.logoutAuthor(dto);
    }

    @PostMapping("/home")
    public String home(){
        return "This is Home Page";
    }

    @GetMapping("/admin")
    public String admin(){
        return "This is Admin Page";
    }


}
