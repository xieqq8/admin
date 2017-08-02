package com.xxx.admin.interfaces.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("/tt")
    public @ResponseBody
    String index() {
        return "你好，这是第一个spring boot应用1111";
    }
}
