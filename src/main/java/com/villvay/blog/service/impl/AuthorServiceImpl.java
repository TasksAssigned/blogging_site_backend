package com.villvay.blog.service.impl;

import com.villvay.blog.dto.AuthorDTO;
import com.villvay.blog.dto.AuthorRegisterDTO;
import com.villvay.blog.dto.LoginDTO;
import com.villvay.blog.entity.Author;
import com.villvay.blog.repository.AuthorRepository;
import com.villvay.blog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-24
 **/
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Override
    public ResponseEntity getAllAuthors() {
        List<AuthorDTO> authorDTOS = new ArrayList<>();
        authorRepository.findAll().forEach(n -> {
            authorDTOS.add(new AuthorDTO(n.getId().intValue(), n.getName(), n.getUsername(), n.getEmail(), n.getAddress()));
        });
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getAuthor(Integer id) {
        Optional<Author> savedAuthor = authorRepository.findById(id.longValue());
        if (savedAuthor.isPresent()) {
            Author author = savedAuthor.get();
            return new ResponseEntity<>(new AuthorDTO(author.getId().intValue(), author.getName(), author.getUsername(), author.getEmail(),
                    author.getAddress()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no such user", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity addNewAuthor(AuthorRegisterDTO dto) {
        ResponseEntity responseByUsername = validateUsername(dto.getUsername());

        if (responseByUsername != null) {
            return responseByUsername;
        }

        ResponseEntity responseByEmail = validateEmail(dto.getEmail());
        if (responseByEmail != null) {
            return responseByEmail;
        }

        Author author = new Author();
        author.setName(dto.getName());
        author.setUsername(dto.getUsername());
        author.setEmail(dto.getEmail());
        author.setAddress(dto.getAddress());
        String encodedPassword  = new BCryptPasswordEncoder().encode(dto.getPassword());
        author.setPassword(encodedPassword);
        author.setRole("USER");
        authorRepository.save(author);
        return new ResponseEntity<>("Saved Successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateAuthor(AuthorRegisterDTO dto, Integer id) {
        Optional<Author> existingAuthor = authorRepository.findById(id.longValue());
        if (!existingAuthor.isPresent()) {
            return new ResponseEntity<>("There is no such user", HttpStatus.BAD_REQUEST);
        }

        Author author = existingAuthor.get();
        if(author.isLoggedIn()==false){
            return new ResponseEntity<>("Need to Login to perform this action", HttpStatus.BAD_REQUEST);
        }

        if (!dto.getUsername().equals(author.getUsername())) {
            ResponseEntity responseByUsername = validateUsername(dto.getUsername());
            if (responseByUsername != null) {
                return responseByUsername;
            }
            author.setUsername(dto.getUsername());
        }

        if(!dto.getEmail().equals(author.getEmail())){
            ResponseEntity responseByEmail = validateEmail(dto.getEmail());
            if(responseByEmail!=null){
                return responseByEmail;
            }
            author.setEmail(dto.getEmail());
        }

        author.setName(dto.getName());
        author.setAddress(dto.getAddress());

        if(!new BCryptPasswordEncoder().matches(dto.getPassword(), author.getPassword())){
            String encodedPassword  = new BCryptPasswordEncoder().encode(dto.getPassword());
            author.setPassword(encodedPassword);
        }

        authorRepository.save(author);
        return new ResponseEntity<>("Updated Successfully", HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity deleteAuthor(Integer id) {
        Optional<Author> existingAuthor = authorRepository.findById(id.longValue());
        if (!existingAuthor.isPresent()) {
            return new ResponseEntity<>("There is no such user", HttpStatus.BAD_REQUEST);
        }
        if(existingAuthor.get().isLoggedIn()==false){
            return new ResponseEntity<>("Need to Login to perform this action", HttpStatus.BAD_REQUEST);
        }
        authorRepository.deleteById(id.longValue());
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity loginAuthor(LoginDTO dto) {
        Author author = authorRepository.findByUsername(dto.getUsername());
        if(author==null || !new BCryptPasswordEncoder().matches(dto.getPassword(), author.getPassword())){
            return new ResponseEntity<>("Username or Password is Incorrect ", HttpStatus.BAD_REQUEST);
        }
        author.setLoggedIn(true);
        authorRepository.save(author);
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

    @Override
    public ResponseEntity logoutAuthor(LoginDTO dto) {
        Author author = authorRepository.findByUsername(dto.getUsername());
        if(author==null || !new BCryptPasswordEncoder().matches(dto.getPassword(), author.getPassword())){
            return new ResponseEntity<>("Username or Password is Incorrect ", HttpStatus.BAD_REQUEST);
        }
        author.setLoggedIn(false);
        authorRepository.save(author);
        return new ResponseEntity<>("Logout Successful", HttpStatus.OK);
    }

    public ResponseEntity validateUsername(String username) {
        boolean usernameExist = authorRepository.existsAuthorByUsername(username);
        if (usernameExist) {
            return new ResponseEntity<>("USER_NAME_ALREADY_EXIST ", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    public ResponseEntity validateEmail(String email) {
        boolean emailExist = authorRepository.existsAuthorByEmail(email);
        if (emailExist) {
            return new ResponseEntity<>("EMAIL_ALREADY_EXIST ", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
