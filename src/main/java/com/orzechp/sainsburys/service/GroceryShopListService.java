package com.orzechp.sainsburys.service;

import com.orzechp.sainsburys.domain.Result;

import java.io.IOException;


public interface GroceryShopListService {
    Result getSainsburysGroceryList() throws  IOException;

}
