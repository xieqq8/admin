package org.bumishi.admin.interfaces.blogsite.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.http.Response;
import org.apache.commons.lang3.StringUtils;
import org.bumishi.admin.config.ApplicationConfig;
import org.bumishi.admin.infrastructure.RequestMapToJsonUtil;
import org.bumishi.admin.interfaces.blogsite.BlogSiteRestTemplate;
import org.bumishi.toolbox.model.PageModel;
import org.bumishi.toolbox.model.RestResponse;
import org.bumishi.toolbox.qiniu.QiNiuApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Random;

/**
 * @author qiang.xie
 * @date 2016/12/7
 */
@Controller
@RequestMapping("/blogsite/book")
public class BookController {

    private Logger logger= LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BlogSiteRestTemplate restTemplate;

    @Autowired
    protected QiNiuApi qiNiuApi;

    @Autowired
    protected ApplicationConfig applicationConfig;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @ResponseBody
    public RestResponse create(HttpServletRequest request,@RequestParam("image") MultipartFile file) {
        RestResponse uploadResponse=upload(file);
        if(!uploadResponse.isSuccess()) {
            return uploadResponse;
        }
        Map<String,String[]> params=request.getParameterMap();
        String json = RequestMapToJsonUtil.toJson(params);
        JSONObject jsonObject= JSON.parseObject(json);
        jsonObject.put("img",applicationConfig.getQiniu_domain()+"/"+uploadResponse.getData().toString());
        return restTemplate.post("/admin/book/add", jsonObject.toJSONString());

    }

    @RequestMapping(value = "/{id}/modify", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse modify(@PathVariable("id") String id, @RequestParam(value = "image",required = false) MultipartFile file,HttpServletRequest request) {
        Map<String,String[]> params=request.getParameterMap();
        String json = RequestMapToJsonUtil.toJson(params);
        if(file==null){
            return restTemplate.post("/admin/book/" + id + "/update", json, id);
        }
        RestResponse uploadResponse=upload(file);
        if(!uploadResponse.isSuccess()) {
            return uploadResponse;
        }

        JSONObject jsonObject= JSON.parseObject(json);
        jsonObject.put("img",applicationConfig.getQiniu_domain()+"/"+uploadResponse.getData().toString());
        return restTemplate.post("/admin/book/" + id + "/update", jsonObject.toJSONString(), id);
    }

    private RestResponse upload(MultipartFile file){
        String contentType=file.getContentType();
        logger.info("upload type:"+contentType);
        String key = System.currentTimeMillis() + "" + new Random().nextInt(10000) + file.getOriginalFilename();
        try {
            Response response = qiNiuApi.upload(file.getBytes(), key, applicationConfig.getQiniu_bucket());
            if(response==null || !response.isOK()){
                return RestResponse.fail("upload fail:"+response.bodyString());
            }
            return RestResponse.ok(key);

        }catch (Exception e){
            return RestResponse.fail("upload fail:"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse delete(@PathVariable("id") String id) {
        return restTemplate.postWithEmptyBody("/admin/book/" + id + "/delete");
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String toform(@RequestParam(value = "id", required = false) String id, Model model) {
        String api = "/blogsite/book/add";
        boolean isUpdata=false;
        if (StringUtils.isNotBlank(id)) {
            RestResponse restResponse=restTemplate.getForObject("/admin/book/" + id);
            model.addAttribute("rep", restResponse);
            if(restResponse!=null && restResponse.isSuccess() && restResponse.getData()!=null) {
                api = "/blogsite/book/" + id + "/modify";
                isUpdata = true;
            }
        }
        model.addAttribute("isUpdate",isUpdata);//是否是新增还是修改，简化模板中的判断
        model.addAttribute("api", api);
        model.addAttribute("catalogs",restTemplate.getForObject("/admin/catalog").getData());
        return "blogsite/book/form";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        RestResponse<PageModel> response = restTemplate.getForPageModelResponseObject("/admin/book?page=" + page);
        model.addAttribute("rep", response);
        return "blogsite/book/list";
    }


    //book index
    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}/indexs")
    public String indexList(Model model, @PathVariable("bookId") String bookId) {
        RestResponse response = restTemplate.getForObject("/admin/book/" + bookId + "/indexs");
        model.addAttribute("rep", response);
        return "blogsite/bookindex/list";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}/indexs/form")
    public String indexform(Model model, @PathVariable("bookId") String bookId, @RequestParam(value = "id", required = false) String id, @RequestParam(value = "parent", required = false) boolean parent) {
        String url = null;
        if (StringUtils.isNotBlank(id) && !parent) {
            model.addAttribute("rep", restTemplate.getForObject("/admin/bookindex/{id}", id));
            url = "/blogsite/book/" + bookId + "/indexs/" + id + "/modify";
        } else {
            url = "/blogsite/book/" + bookId + "/indexs/add";
            if (StringUtils.isNotBlank(id)) {
                model.addAttribute("parentPath", id);
            }
        }
        model.addAttribute("bookId", bookId);
        model.addAttribute("api", url);
        return "blogsite/bookindex/form";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/{bookId}/indexs/add")
    @ResponseBody
    public RestResponse createIndex(@PathVariable("bookId") String bookId, @RequestBody String json) {
        return restTemplate.post("/admin/bookindex/{bookId}/add", json, bookId);
    }


    @RequestMapping(value = "/{bookId}/indexs/{id}/modify", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse modifyIndex(@PathVariable("id") String id, @RequestBody String json) {
        return restTemplate.post("/admin/bookindex/{id}/modify", json, id);
    }


    @RequestMapping(value = "/indexs/{id}/status", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public RestResponse switchStatus(@PathVariable("id") String id, @RequestParam("disable") boolean disable) {
        return restTemplate.postWithEmptyBody("/admin/bookindex/{id}/status?disable=" + disable, id);
    }

    @RequestMapping(value = "/indexs/{id}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public RestResponse deleteIndex(@PathVariable("id") String id) {
        return restTemplate.postWithEmptyBody("/admin/bookindex/{id}/delete", id);
    }
}
