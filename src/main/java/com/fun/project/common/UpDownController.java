package com.fun.project.common;

import com.alibaba.fastjson.JSONObject;
import com.fun.common.result.CommonResult;
import com.fun.common.utils.ServletUtils;
import com.fun.common.utils.StringUtils;
import com.fun.common.utils.file.FileUploadUtils;
import com.fun.common.utils.file.FileUtils;
import com.fun.framework.config.FunBootConfig;
import com.fun.framework.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用上传下载
 *
 * @author DJun
 */
@RestController
public class UpDownController {
    private Logger log = LoggerFactory.getLogger(UpDownController.class);

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("/common/download" )
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                CommonResult commonResult = CommonResult.failed(StringUtils.format("文件名称({})非法，不允许下载。 " , fileName));
                ServletUtils.renderString(response, JSONObject.toJSONString(commonResult));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_" ) + 1);
            String filePath = FunBootConfig.getDownloadPath() + "/" + fileName;
            response.setContentType("multipart/form-data" );
            response.setHeader("Content-Disposition" ,
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败，Error:{}" , e.getMessage());
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload" )
    @ResponseBody
    public CommonResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = FunBootConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            JSONObject ajax = new JSONObject();
            ajax.put("fileName" , fileName);
            ajax.put("url" , url);
            return CommonResult.success(ajax);
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
    }

}
