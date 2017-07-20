package org.bumishi.admin.interfaces.blogsite.web;

import org.apache.commons.lang3.StringUtils;
import org.bumishi.admin.interfaces.blogsite.BlogSiteRestTemplate;
import org.bumishi.toolbox.model.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author qiang.xie
 * @date 2016/9/18
 */
@Controller
@RequestMapping("/blogsite/catalog")
public class CatalogController {


    @Autowired
    private BlogSiteRestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @ResponseBody
    public RestResponse create(@RequestBody String json) {
        return restTemplate.post("/admin/catalog/add", json);
    }


    @RequestMapping(value = "/{id}/modify", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse modify(@PathVariable("id") String id, @RequestBody String json) {
        return restTemplate.post("/admin/catalog/{id}/modify", json, id);
    }


    @RequestMapping(value = "/{id}/status", method = {RequestMethod.POST,RequestMethod.PUT})
    @ResponseBody
    public RestResponse switchStatus(@PathVariable("id") String id, @RequestParam("disable") boolean disable) {
        return restTemplate.postWithEmptyBody("/admin/catalog/{id}/status?disable=" + disable, id);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse delete(@PathVariable("id") String id) {
        return restTemplate.postWithEmptyBody("/admin/catalog/{id}/delete", id);
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String toform(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "parent", required = false) boolean parent, Model model) {
        String url = null;
        if (StringUtils.isNotBlank(id) && !parent) {
            model.addAttribute("rep", restTemplate.getForObject("/admin/catalog/{id}",id));
            url = "/blogsite/catalog/" + id + "/modify";
        } else {
            url = "/blogsite/catalog/add";
            if (StringUtils.isNotBlank(id)) {
                model.addAttribute("parentPath", id);
            }
        }
        model.addAttribute("api", url);
        return "blogsite/catalog/form";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        RestResponse response = restTemplate.getForObject("/admin/catalog");
        model.addAttribute("rep",response);
        return "blogsite/catalog/list";
    }


}
