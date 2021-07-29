//package com.example.test.service;
//
//import com.example.test.entity.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//public class UserDetailObj implements UserDetails {
//
//    private User user;
//
//    public UserDetailObj(User user) {
//        this.user = user;
//    }
//
//    // 해당 유저의 권한을 return하는 곳
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return user.getRole();
//            }
//        });
//        return collect;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUsername();
//    }
//
//    // 계정이 만료되었는가?
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    // 계정이 잠겼는가?
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    // 이 계정의 만료기간을 지났는가?
//    // 계정 비밀번호를 오래 사용한건 아닌가?
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    // 계정이 활성화 되어있는가?
//    @Override
//    public boolean isEnabled() {
//        // ex 회원이 1년동안 로그인을 안해서 휴면 계정으로
//        return true;
//    }
//}

package com.example.test.service;

import com.example.test.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailObj implements UserDetails {
    private User user;
    public UserDetailObj(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return true; }
}
