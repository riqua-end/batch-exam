package com.example.batchexam.domain;

import lombok.Getter;

@Getter
public enum ServicePolicy {
    A(1L,"/batchtest/services/a",10),
    B(2L,"/batchtest/services/b",10),
    C(3L,"/batchtest/services/c",10),
    D(4L,"/batchtest/services/d",15),
    E(5L,"/batchtest/services/e",15),
    F(6L,"/batchtest/services/f",10),
    G(7L,"/batchtest/services/g",10),
    H(8L,"/batchtest/services/h",10),
    I(9L,"/batchtest/services/i",10),
    J(10L,"/batchtest/services/j",10),
    K(11L,"/batchtest/services/k",10),
    L(12L,"/batchtest/services/l",12),
    M(13L,"/batchtest/services/m",12),
    N(14L,"/batchtest/services/n",12),
    O(15L,"/batchtest/services/o",10),
    P(16L,"/batchtest/services/p",10),
    Q(17L,"/batchtest/services/q",10),
    R(18L,"/batchtest/services/r",10),
    S(19L,"/batchtest/services/s",10),
    T(20L,"/batchtest/services/t",10),
    U(21L,"/batchtest/services/u",10),
    V(22L,"/batchtest/services/v",10),
    W(23L,"/batchtest/services/w",19),
    X(24L,"/batchtest/services/x",19),
    Y(25L,"/batchtest/services/y",19),
    Z(26L,"/batchtest/services/z",19);

    private final Long id;
    private final String url;
    private final Integer fee;

    ServicePolicy(Long id, String url, Integer fee) {
        this.id = id;
        this.url = url;
        this.fee = fee;
    }
}
