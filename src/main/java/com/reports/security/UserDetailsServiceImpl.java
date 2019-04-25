package com.reports.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reports.dao.UsersDao;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersDao studentDAO;

    @Override
    public UserDetails loadUserByUsername(String rollNo) throws UsernameNotFoundException {
        com.reports.entity.Users user = studentDAO.findById(rollNo);
        if (null == user) {
            throw new UsernameNotFoundException("UserName not found " + rollNo);
        }

        return new User(user.getRollNo(), user.getPassword(), user.getAuthorities());
    }

}
