package com.example.batchexam.application.dormant;

import com.example.batchexam.customer.Customer;
import com.example.batchexam.email.EmailProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreDormantBatchItemWriterTest {

    private PreDormantBatchItemWriter preDormantBatchItemWriter;

    @Test
    @DisplayName("1주일 뒤에 휴면 계정 전환 예정자라고 이메일을 전송해야 한다")
    void test1(){

        // given
        final EmailProvider mockEmailProvider = mock(EmailProvider.class);
        this.preDormantBatchItemWriter = new PreDormantBatchItemWriter(mockEmailProvider);

        final Customer customer = new Customer("홍길동", "b@b.com");
        // when
        preDormantBatchItemWriter.write(customer);

        // then
        // send 가 적어도 1번 이상 호출되었는지 검증
        verify(mockEmailProvider, atLeast(1)).send(any(), any(), any());

    }

}