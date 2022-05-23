package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.config.ApplicationProperties;
import com.ruowei.ecsp.service.FileService;
import com.ruowei.ecsp.util.StringUtil;
import com.ruowei.ecsp.util.Utils;
import com.ruowei.ecsp.web.rest.errors.BadRequestProblem;
import com.ruowei.ecsp.web.rest.vm.FileVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api")
@Transactional
@Api(tags = "文件管理")
public class FileResource {

    private final Logger log = LoggerFactory.getLogger(FileResource.class);

    private final ApplicationProperties applicationProperties;
    private final FileService fileService;

    public FileResource(ApplicationProperties applicationProperties, FileService fileService) {
        this.applicationProperties = applicationProperties;
        this.fileService = fileService;
    }

    @PostMapping("/file/upload")
    @ApiOperation(value = "普通文件上传接口", notes = "作者：林宏栋")
    public ResponseEntity<String> uploadFile(
        @RequestParam MultipartFile file,
        @ApiParam(value = "文件类别编码", required = true) @RequestParam String fileCategoryCode,
        @ApiParam(value = "文件名称") @RequestParam(required = false) String fileName
    ) {
        boolean isAdmin = fileCategoryCode != null && (fileCategoryCode.equals("method") || fileCategoryCode.equals("bgcImg") || fileCategoryCode.equals("logo"));
        return fileService.uploadFile(file, fileCategoryCode, fileName, isAdmin);
    }


    @PostMapping(value = "/yingyongUpload", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public ResponseEntity<String> uploadAvatar(HttpServletRequest request, @RequestParam("fileCategoryCode") String fileCategoryCode) {
        List<String> result = new ArrayList<>();
        try {
            Path uploadDir = Paths.get(applicationProperties.getUploadPath());
            if (!new File(applicationProperties.getUploadPath() + StringUtil.camelCase(fileCategoryCode)).exists()) {
                Files.createDirectories(uploadDir.resolve(StringUtil.camelCase(fileCategoryCode)));
            }
            String basePath = applicationProperties.getUploadPath() + StringUtil.camelCase(fileCategoryCode) + "/";
            request.setCharacterEncoding("UTF-8");
            MultipartRequest multiReq = (MultipartRequest) request;
            Iterator<String> files = multiReq.getFileNames();
            String url = "";
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            while (files.hasNext()) {
                List<MultipartFile> multipartFiles = multiReq.getFiles(files.next());
                for (MultipartFile multipartFile : multipartFiles) {
                    String path = basePath;
                    String file_extension = "", file_nm = "";
                    String originName = multipartFile.getOriginalFilename();
                    file_extension = (originName.substring(originName.lastIndexOf(".") + 1)).toLowerCase();
                    file_nm = Utils.getUUID();
                    url = date + "/";

                    File f = new File(path + url);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    url = url + file_nm + "." + file_extension;
                    path = path + url;

                    // 上传文件
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(path);
                        if (!multipartFile.isEmpty()) {
                            fos.write(multipartFile.getBytes());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("文件上传错误文件上传错误。");
                    } finally {
                        if (fos != null) {
                            try {
                                fos.flush();
                                fos.close();
                                fos = null;
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("文件上传错误文件上传错误。");
                            }
                        }
                    }
                    result.add(url);
                }
            }
            return ResponseEntity.ok().body(basePath + result.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传错误文件上传错误。");
        }
        return null;
    }

    @PostMapping("/file")
    @ApiOperation(value = "普通文件删除接口", notes = "作者：林宏栋")
    public ResponseEntity<Void> deleteFile(@RequestBody FileVM vm) {
        try {
            Files.deleteIfExists(
                Paths.get(applicationProperties.getUploadPath().concat(vm.getPath().replaceFirst("/upload/", "")))
            );
        } catch (IOException e) {
            log.info("删除文件失败：{}，异常信息：{}", vm.getPath().substring(vm.getPath().lastIndexOf("/") + 1), e.getCause());
            throw new BadRequestProblem("删除文件失败");
        }
        return ResponseEntity.noContent().build();
    }
}
