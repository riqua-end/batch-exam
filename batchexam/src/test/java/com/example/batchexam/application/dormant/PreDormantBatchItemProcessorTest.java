package com.example.batchexam.application.dormant;

import com.example.batchexam.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PreDormantBatchItemProcessorTest {

    private PreDormantBatchItemProcessor preDormantBatchItemProcessor;

    @BeforeEach
    void setup(){
        preDormantBatchItemProcessor = new PreDormantBatchItemProcessor();
    }

    @Test
    @DisplayName("로그인 날짜가 오늘로부터 358일 전이면 customer 를 반환해야 한다")
    void test1(){

        // given
        final Customer customer = new Customer("홍길동", "a@a.com");
        // 오늘은 2024.05.15 예정자는 2023.05.23
        customer.setLoginAt(LocalDateTime.now().minusDays(365).plusDays(7));

        // when
        final Customer result = preDormantBatchItemProcessor.process(customer);
        // then
        assertThat(result).isEqualTo(customer);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("로그인 날짜가 오늘로부터 358일 전이 아니면 null 을 반환해야 한다.")
    void test2(){

        // given
        final Customer customer = new Customer("홍길동", "a@a.com");
        // when
        final Customer result = preDormantBatchItemProcessor.process(customer);
        // then
        assertThat(result).isNull();
    }
}