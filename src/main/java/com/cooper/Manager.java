package com.cooper;

import java.util.List;

public class Manager {

    public List<SingleOrder> processOrder(Order order) {
        return order.getSingleOrder();
    }

}
