package com.ruowei.ecsp.service;

import com.ruowei.ecsp.repository.EcoQualityProjectRepository;
import com.ruowei.ecsp.repository.NewsRepository;
import com.ruowei.ecsp.util.ListUtil;
import com.ruowei.ecsp.util.StreamUtil;
import com.ruowei.ecsp.util.StringUtil;
import com.ruowei.ecsp.web.rest.dto.NewsProjectDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
public class NewsProjectService {
    private final NewsRepository newsRepository;
    private final EcoQualityProjectRepository ecoQualityProjectRepository;

    public NewsProjectService(NewsRepository newsRepository, EcoQualityProjectRepository ecoQualityProjectRepository) {
        this.newsRepository = newsRepository;
        this.ecoQualityProjectRepository = ecoQualityProjectRepository;
    }

    public ResponseEntity<List<NewsProjectDTO>> searchBy(String name) {
        name = StringUtil.isNotBlank(name) ? "%" + name + "%" : null;
        List<NewsProjectDTO> dtos = newsRepository.searchByTitle(name);
        Stream.of("林草新闻", "地方动态", "政策法规").forEach(type -> {
            Optional<NewsProjectDTO> optional = dtos.stream().filter(dto -> dto.getType().equals(type)).findFirst();
            if (optional.isEmpty()) {
                dtos.add(new NewsProjectDTO(type, 0L));
            }
        });
        long projectCount = ecoQualityProjectRepository.searchByName(name);
        dtos.add(new NewsProjectDTO("项目展示", projectCount));
        return ResponseEntity.ok().body(dtos);
    }


}
