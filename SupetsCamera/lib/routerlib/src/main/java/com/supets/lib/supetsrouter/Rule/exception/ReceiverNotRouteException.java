package com.supets.lib.supetsrouter.Rule.exception;

/**
 * Created by qibin on 2016/10/8.
 */

public class ReceiverNotRouteException extends NotRouteException {

    public ReceiverNotRouteException(String pattern) {
        super("receiver", pattern);
    }
}
