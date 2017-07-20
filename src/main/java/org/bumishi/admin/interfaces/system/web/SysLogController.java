package org.bumishi.admin.interfaces.system.web;

import org.bumishi.admin.application.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qiang.xie
 * @date 2016/9/18
 */
@Controller
@RequestMapping("/syslog")
public class SysLogController {

    @Autowired
    protected SysLogService sysLogService;

    

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void clear(){
         sysLogService.clear();
    }

   
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model)
    {
        model.addAttribute("list",sysLogService.list());
        return "/syslog/list";
    }


}
