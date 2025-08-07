package com.lvrgese.news_aggregator.exception;

public class InvalidJwtTokenException extends RuntimeException{

    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
