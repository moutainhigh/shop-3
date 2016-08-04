package com.tuisitang.modules.shop.front.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tuisitang.common.annotation.Token;

/**
 * <p>
 * 防止重复提交过滤器
 * </p>
 *
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class );
            if (annotation != null ) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    request.getSession( false ).setAttribute("_token" , UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                    	response.sendRedirect("waiting");
                        return false ;
                    }
                    request.getSession( false ).removeAttribute("_token" );
                }
            }
            return true ;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession( false ).getAttribute( "_token" );
        if (serverToken == null ) {
            return true ;
        }
        String clinetToken = request.getParameter( "_token" );
        if (clinetToken == null ) {
            return true ;
        }
        if (!serverToken.equals(clinetToken)) {
            return true ;
        }
        return false ;
    } 
}