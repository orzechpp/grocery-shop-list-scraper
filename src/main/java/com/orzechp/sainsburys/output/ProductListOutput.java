package com.orzechp.sainsburys.output;

import com.orzechp.sainsburys.exceptions.ProductListException;
import com.orzechp.sainsburys.domain.Result;

public interface ProductListOutput {
    void outputProductList(Result result) throws ProductListException;
}
