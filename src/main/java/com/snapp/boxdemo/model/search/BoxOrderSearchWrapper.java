package com.snapp.boxdemo.model.search;

import com.snapp.boxdemo.model.entity.OrderType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BoxOrderSearchWrapper {
    private String ownerFullName;
    private String ownerId;
    private String ownerPhoneNumber;
    private OrderType orderType;
    private Date creationDate;
}
