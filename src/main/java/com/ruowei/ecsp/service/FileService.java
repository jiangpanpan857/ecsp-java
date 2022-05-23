package com.ruowei.ecsp.service;

import com.ruowei.ecsp.config.ApplicationProperties;
import com.ruowei.ecsp.util.StringUtil;
import com.ruowei.ecsp.web.rest.errors.BadRequestProblem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileService {
    private final ApplicationProperties applicationProperties;

    public FileService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public ResponseEntity<String> uploadFile(MultipartFile file,
                                             String fileCategoryCode,
                                             String fileName,
                                             boolean isAdmin) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename() == null ? file.getName() : file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new BadRequestProblem("无法保存空文件", filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new BadRequestProblem("无法将文件保存到指定路径外", filename);
            }
            String uploadPath = isAdmin ? applicationProperties.getEcspUploadPath() : applicationProperties.getUploadPath();
            Path uploadDir = Paths.get(uploadPath);
            if (!new File(uploadPath + StringUtil.camelCase(fileCategoryCode)).exists()) {
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
            String prePath = isAdmin ? "/ecsp/upload/" : "/upload/";
            return ResponseEntity.ok().body(prePath + StringUtil.camelCase(fileCategoryCode) + "/" + newFile.getName());
        } catch (IOException e) {
            log.info("保存文件失败：{}，异常信息：{}", filename, e.getCause());
            throw new BadRequestProblem("保存文件失败", filename);
        }
    }
}
