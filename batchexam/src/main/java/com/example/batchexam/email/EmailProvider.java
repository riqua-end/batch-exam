package com.example.batchexam.email;

import lombok.extern.slf4j.Slf4j;

public interface EmailProvider {

    void send(String emailAddress, String title, String body);

    // email 전송을 하지 않고 단순 로깅으로 처리
    @Slf4j
    class Fake implements EmailProvider {

        @Override
        public void send(String emailAddress, String title, String body) {
            log.info("{} email 전송 완료 ! {} : {} ", emailAddress,title,body);
        }
    }
}
