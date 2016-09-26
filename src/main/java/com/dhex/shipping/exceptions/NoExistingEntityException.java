package com.dhex.shipping.exceptions;

/**
 * Created by angel on 25/09/16.
 */
public class NoExistingEntityException extends RuntimeException {
    public NoExistingEntityException(String message){
        super(message);
    }
}
