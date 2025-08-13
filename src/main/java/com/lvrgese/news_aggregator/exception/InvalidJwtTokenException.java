package com.lvrgese.news_aggregator.exception;

public class InvalidJwtTokenException extends Exception{

    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
