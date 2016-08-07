package com.orzechp.sainsburys.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ZERO;

@Data
public class Result {

    public static final int NEW_SCALE = 2;
    @SerializedName("results")
    private List<Product> products;
    private double total;

    public Result(List<Product> products) {
        this.products = products;
        this.total = getTotal(products);
    }

    private double getTotal(List<Product> products) {
        BigDecimal total = ZERO;
        for (Product product : products) {
            total = total.add(new BigDecimal(product.getUnitPrice()));
        }
        return total.setScale(NEW_SCALE, ROUND_HALF_UP).doubleValue();
    }

}
