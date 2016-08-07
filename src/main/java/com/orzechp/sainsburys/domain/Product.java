package com.orzechp.sainsburys.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Product {
    private String title;
    private String size;
    @SerializedName("unit_price")
    private double unitPrice;
    private String description;

}
