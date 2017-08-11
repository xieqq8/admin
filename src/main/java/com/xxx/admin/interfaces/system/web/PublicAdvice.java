package com.xxx.admin.interfaces.system.web;

import com.alibaba.fastjson.JSON;
import com.xxx.admin.application.MenuService;
import com.xxx.admin.security.SecurityUser;
import com.xxx.admin.security.SecurityUtil;
import com.xxx.toolbox.model.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiexx
 * @date 2016/9/27
 */
@ControllerAdvice
public class PublicAdvice {
    protected Logger logger= org.slf4j.LoggerFactory.getLogger("xadmin_error_logger");
    @Autowired
    protected MenuService menuService;

    /**
     * 异常的统一处理
     * @param request
     * @param response
     * @param ex
     * @throws IOException
     */
    @ExceptionHandler
    public void handleControllerException(HttpServletRequest request, HttpServletResponse response, Throwable ex) throws IOException {
        logger.error("handleControllerException,url:{}",request.getRequestURI(),ex);
        String ajax = request.getHeader("X-Requested-With");
        response.setCharacterEncoding("utf-8");
        if (StringUtils.isBlank(ajax)) {
            response.sendRedirect("/error");
        } else {
            String json= JSON.toJSONString(RestResponse.fail("出错了:" + ex.getMessage()));
            response.setContentType("application/json");
            response.getWriter().println(json);
        }

    }

    @ModelAttribute
    public void addCommonModel(Model model, HttpServletRequest request) {
//        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");
        SecurityUser user = SecurityUtil.getUser();
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("navs", menuService.getNavMenus(user.getUid()));
        }
    }

//    @ModelAttribute
//    public User newUser() {
//        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");
//        return new User();
//    }
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
//    }
//
//    @ExceptionHandler(UnauthenticatedException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public String processUnauthenticatedException(NativeWebRequest request, UnauthenticatedException e) {
//        System.out.println("===========应用到所有@RequestMapping注解的方法，在其抛出UnauthenticatedException异常时执行");
//        return "viewName"; //返回一个逻辑视图名
//    }

}
