package org.bumishi.admin.interfaces.system.web;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.bumishi.admin.config.ApplicationConfig;
import org.bumishi.toolbox.image.ImageUtils;
import org.bumishi.toolbox.qiniu.QiNiuApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

/**
 * @author qiang.xie
 * @date 2016/12/9
 */
@RequestMapping("/upload")
@RestController
public class UploadController {

    @Autowired
    protected QiNiuApi qiNiuApi;

    @Autowired
    protected ApplicationConfig applicationConfig;

    @PostMapping("/image")
    public UploadResponse uploadImage(@RequestParam("editormd-image-file") MultipartFile file) throws IOException {
        String contentType=file.getContentType();
        System.out.println(contentType);
        String key = System.currentTimeMillis() + "" + new Random().nextInt(10000) + file.getOriginalFilename();
        byte[] withWaterImage= ImageUtils.addWaterForImage(file.getInputStream(),new ClassPathResource("/water.png").getInputStream());
        Response response = qiNiuApi.upload(withWaterImage, key, applicationConfig.getQiniu_bucket());
        return create(response, key);
    }

    private UploadResponse create(Response response, String filename) {
        UploadResponse uploadResponse = new UploadResponse();
        if (response == null || !response.isOK()) {

            uploadResponse.setSuccess(0);
            try {
                uploadResponse.setMessage(response.bodyString());
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        } else {

            uploadResponse.setSuccess(1);
            uploadResponse.setUrl(applicationConfig.getQiniu_domain() + "/" + filename);
        }
        return uploadResponse;
    }

    public class UploadResponse {

        //        {
//            success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
//                    message : "提示的信息，上传成功或上传失败及错误信息等。",
//                url     : "图片地址"        // 上传成功时才返回
//        }
        public int success;

        public String url;

        public String message;

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
