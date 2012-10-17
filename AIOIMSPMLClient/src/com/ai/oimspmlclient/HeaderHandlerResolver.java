/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ai.oimspmlclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 *
 * @author www.javadb.com
 */
public class HeaderHandlerResolver implements HandlerResolver {

    private String userId;
    private String password;

    public HeaderHandlerResolver(String userId,String password) {
        this.userId = userId;
        this.password = password;
    }

public List<Handler> getHandlerChain(PortInfo portInfo) {
      List<Handler> handlerChain = new ArrayList<Handler>();

      HeaderHandler hh = new HeaderHandler(userId,password);

      handlerChain.add(hh);

      return handlerChain;
   }
}