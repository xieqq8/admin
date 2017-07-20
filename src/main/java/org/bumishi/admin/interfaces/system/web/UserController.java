package org.bumishi.admin.interfaces.system.web;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.bumishi.admin.application.MenuService;
import org.bumishi.admin.application.UserService;
import org.bumishi.admin.interfaces.system.facade.assembler.UserAssembler;
import org.bumishi.admin.interfaces.system.facade.commondobject.ProfileCommand;
import org.bumishi.admin.interfaces.system.facade.commondobject.UserCommond;
import org.bumishi.admin.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author qiang.xie
 * @date 2016/9/18
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected MenuService menuService;

    @RequestMapping(method = RequestMethod.POST,value = "/add")
    public String create( UserCommond user){
        userService.create(UserAssembler.commondToDomain(user));
        return "redirect:/user";
    }

    @RequestMapping(value = "/{id}/modify", method = RequestMethod.POST)
    public String modify(@PathVariable("id") String id,  UserCommond user) {
        userService.modify(UserAssembler.commondToDomain(id, user));
        return "redirect:/user";
    }

    @RequestMapping(value = "/{id}/status",method = RequestMethod.PUT)
    @ResponseBody
    public void switchStatus(@PathVariable("id") String id,@RequestParam("disable") boolean disable){
        userService.switchStatus(id,disable);
    }
    @RequestMapping(value = "/{id}/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id")String id){
         userService.delete(id);
    }

    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String form(@RequestParam(value = "id",required = false)String id, Model model){
        String api="/user/add";
        if(StringUtils.isNotBlank(id)){
            model.addAttribute("acount",UserAssembler.domainToDto(userService.get(id)));
            api="/user/"+id+"/modify";
        }
        model.addAttribute("api",api);
        return  "user/form";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model){
        model.addAttribute("list",UserAssembler.domainToDto(userService.list()));
        return "user/list";
    }

    @RequestMapping(value = "/{id}/grant-role",method = RequestMethod.POST)
    public String grantRole(@PathVariable("id") String id,  String[] rid) {
        if(rid==null){
            rid=new String[0];
        }
        userService.grantRole(id, Lists.newArrayList(rid));
        return "redirect:/user";
    }


    @RequestMapping(value = "/{id}/select-role", method = RequestMethod.GET)
    public String selectRole(@PathVariable("id") String id,Model model) {
        model.addAttribute("list",userService.selectRoles(id));
        model.addAttribute("api","/user/"+id+"/grant-role");
        return "user/grant-role";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String myinfo() {
        return "user/profile";
    }

    @RequestMapping(value = "/modify-profile", method = RequestMethod.POST)
    public String modifyProfile( ProfileCommand myInfo) {
        this.userService.modify(UserAssembler.profileToDomain(SecurityUtil.getUid(), myInfo));
        SecurityUtil.getUser().setEmail(myInfo.getEmail());
        return "user/profile";
    }


}
