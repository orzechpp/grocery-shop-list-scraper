package com.orzechp.sainsburys.runner;

import com.orzechp.sainsburys.output.ProductListOutput;
import com.orzechp.sainsburys.service.GroceryShopListService;
import com.orzechp.sainsburys.domain.Result;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class GroceryShopListRunner implements CommandLineRunner {

    @Autowired
    private  GroceryShopListService groceryShopListService;
    @Autowired
    private   ProductListOutput productListOutput;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sainsbury's Grocery Shop Console Application started");
        Result products = groceryShopListService.getSainsburysGroceryList();
        productListOutput.outputProductList(products);
        System.out.println("Grocery Shop List outputted successfully.");
    }
}

