package com.example.homework2.service;

import com.example.homework2.dto.MemberCreateRequest;
import com.example.homework2.entity.Member;
import com.example.homework2.exception.DuplicateEmailException;
import com.example.homework2.repository.MemberRepository;
import com.example.homework2.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(MemberCreateRequest request){
        // 1. 이메일 중복 확인
        if (memberRepository.existsByEmail(request.getEmail())) {
            // 중복 이메일 처리
            throw new DuplicateEmailException(request.getEmail());
        }

        // 2. 비밀번호 BCrypt 해시
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 3. Member 생성 후 저장
        Member newMember = new Member(request.getEmail(), encodedPassword, request.getPassword());
        memberRepository.save(newMember);
    };


}
