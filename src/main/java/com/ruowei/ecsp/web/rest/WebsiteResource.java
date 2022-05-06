package com.ruowei.ecsp.web.rest;

import io.swagger.annotations.Api;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/website")
@Transactional
@Api(tags = "网站管理")
public class WebsiteResource {
}
