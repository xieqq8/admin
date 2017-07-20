package org.bumishi.admin.interfaces.intercept;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xieqiang on 2016/11/20.
 */
public class NavMenuActiveInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getServletPath();
        request.setAttribute("currentMenu","system");
        if(url.length()>2) {
            url=url.substring(1);
            String currentMenu =url;
            int index=url.indexOf("/");
            if(index>0) {
                currentMenu = url.substring(0, index);
            }
            request.setAttribute("currentMenu",currentMenu);
        }
        return super.preHandle(request, response, handler);
    }
}
