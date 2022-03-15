package org.Clover.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging<T> {


    public void info(Class<T> senderClass, String message){
        final Logger LOGGER = LoggerFactory.getLogger(senderClass);
        LOGGER.info(message);
    }

    public void error(Class<T> senderClass, String error){
        final Logger LOGGER = LoggerFactory.getLogger(senderClass);
        LOGGER.error(error);
    }

    public void warn(Class<T> senderClass, String warning){
        final Logger LOGGER = LoggerFactory.getLogger(senderClass);
        LOGGER.warn(warning);
    }

    public void debug(Class<T> senderClass, String info){
        final Logger LOGGER = LoggerFactory.getLogger(senderClass);
        LOGGER.debug(info);
    }

    public void trace(Class<T> senderClass, String error){
        final Logger LOGGER = LoggerFactory.getLogger(senderClass);
        LOGGER.trace(error);
    }



}
