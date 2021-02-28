package com.lhy.shopping.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.lhy.shopping.cart.dao.AccountDAO;
import com.lhy.shopping.cart.entity.AccountDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDO account = accountDAO.findAccount(username);

        log.info("登录用户信息 {}", JSON.toJSONString(account));

        if (account == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        // 加密方式保持一致
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

        String role = account.getUserRole();

        List<GrantedAuthority> grantList = new ArrayList<>();

        // ROLE_EMPLOYEE, ROLE_MANAGER, ROLE_CUSTOMER
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        grantList.add(authority);

        boolean enabled = account.getActive();

        return new User(account.getUserName(), account.getPassword(), enabled, true, true, true, grantList);
    }
}
