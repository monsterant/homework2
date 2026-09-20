package com.example.homework2.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("인증된 회원 정보를 찾을 수 없습니다.");
    }
}