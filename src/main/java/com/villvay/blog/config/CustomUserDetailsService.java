package com.villvay.blog.config;

/**
 * @author : Shalitha Anuradha <shalithaanuradha123@gmail.com>
 * @since : 2022-07-26
 **/
import com.villvay.blog.entity.Author;
import com.villvay.blog.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Author author = authorRepository.findByUsername(username);
        if(author ==null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new CustomUserDetails(author);
    }
}
