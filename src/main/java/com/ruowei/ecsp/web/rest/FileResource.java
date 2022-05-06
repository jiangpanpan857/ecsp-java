package com.ruowei.ecsp.web.rest;

import com.ruowei.ecsp.config.ApplicationProperties;
import com.ruowei.ecsp.util.StringUtil;
import com.ruowei.ecsp.web.rest.errors.BadRequestProblem;
import com.ruowei.ecsp.web.rest.vm.FileVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
@Transactional
@Api(tags = "文件管理")
public class FileResource {

    private final Logger log = LoggerFactory.getLogger(FileResource.class);

    private final ApplicationProperties applicationProperties;

    public FileResource(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @PostMapping("/file/upload")
    @ApiOperation(value = "普通文件上传接口", notes = "作者：林宏栋")
    public ResponseEntity<String> uploadFile(
        @RequestParam MultipartFile file,
        @ApiParam(value = "文件类别编码", required = true) @RequestParam String fileCategoryCode,
        @ApiParam(value = "文件名称") @RequestParam(required = false) String fileName
    ) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename() == null ? file.getName() : file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new BadRequestProblem("无法保存空文件", filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new BadRequestProblem("无法将文件保存到指定路径外", filename);
            }
            Path uploadDir = Paths.get(applicationProperties.getUploadPath());
            if (!new File(applicationProperties.getUploadPath() + StringUtil.camelCase(fileCategoryCode)).exists()) {
                Files.createDirectories(uploadDir.resolve(StringUtil.camelCase(fileCategoryCode)));
            }
            String extension = FileNameUtils.getExtension(filename);
            String originName = FileNameUtils.getBaseName(filename);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(fileName)) {
                originName = fileName;
            }
            File newFile = new File(
                String.valueOf(uploadDir.resolve(StringUtil.camelCase(fileCategoryCode) + "/" + originName + "." + extension))
            );
            while (newFile.exists()) {
                int randomNum = RandomUtils.nextInt(1, 1000);
                newFile =
                    new File(
                        String.valueOf(
                            uploadDir.resolve(
                                StringUtil.camelCase(fileCategoryCode) + "/" + originName + randomNum + "." + extension
                            )
                        )
                    );
            }
            Files.copy(file.getInputStream(), newFile.toPath());
            return ResponseEntity.ok().body("/upload/" + StringUtil.camelCase(fileCategoryCode) + "/" + newFile.getName());
        } catch (IOException e) {
            log.info("保存文件失败：{}，异常信息：{}", filename, e.getCause());
            throw new BadRequestProblem("保存文件失败", filename);
        }
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
