package com.otakumap.domain.order.dto;

import com.otakumap.domain.order.entity.Order;
import lombok.Getter;

@Getter
public class OrderDto {
    Long price;
    String impUid;
    String merchantUid;

    public Order toEntity() {
        return Order.builder()
                .price(price)
                .impUid(impUid)
                .merchantUid(merchantUid)
                .build();
    }
}