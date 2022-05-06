package com.ruowei.ecsp.web.rest;

import io.swagger.annotations.Api;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/headline-news")
@Transactional
@Api(tags = "头条新闻管理")
public class HeadlineNewsResource {
}
