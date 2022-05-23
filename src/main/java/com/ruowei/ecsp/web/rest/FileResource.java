package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.config.ApplicationProperties;
import com.ruowei.ecsp.service.FileService;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
