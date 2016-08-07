package com.orzechp.sainsburys.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductListException extends Exception {


    public ProductListException(String message) {
        super(message);
    }

    public ProductListException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductListException(Throwable cause) {
        super(cause);
    }
}
