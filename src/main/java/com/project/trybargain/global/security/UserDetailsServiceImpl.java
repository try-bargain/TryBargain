package com.project.trybargain.global.security;

import com.project.trybargain.domain.user.entity.User;
import com.project.trybargain.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("해당 유저를 찾을 수 없습니다. " + userId));
        return new UserDetailsImpl(user);
    }
}
