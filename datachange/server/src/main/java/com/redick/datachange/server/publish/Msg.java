package com.redick.datachange.server.publish;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liupenghui
 * @date 2022/2/14 5:47 下午
 */
@Data
@AllArgsConstructor
public class Msg<T> {

    private String publisher;

    private T msg;
}
