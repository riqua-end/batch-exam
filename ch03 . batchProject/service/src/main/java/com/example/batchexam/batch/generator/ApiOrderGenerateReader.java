package com.example.batchexam.batch.generator;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@StepScope // jobParameter 를 받기 위함
public class ApiOrderGenerateReader implements ItemReader<Boolean> {

    private Long totalCount;
    private AtomicLong current;

    /**
     * AtomicLong은 Long 자료형을 갖고 있는 Wrapping 클래스이다.
     *
     * Thread-safe로 구현되어 멀티쓰레드에서 synchronized 없이 사용할 수 있다.
     * 또한 synchronized 보다 적은 비용으로 동시성을 보장할 수 있다.
     *
     */

    public ApiOrderGenerateReader(
            @Value("#{jobParameters['totalCount']}") String totalCount
    ) {
        this.totalCount = Long.parseLong(totalCount);
        this.current = new AtomicLong(0 );
    }

    @Override
    public Boolean read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (current.incrementAndGet() > totalCount) // 0을 가져와서 +1 증가 ,current 가 지정한 숫자보다 많으면 null 반환하면서 정지
            return null;

        return true;
    }
}
