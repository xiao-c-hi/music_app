package com.example.courses.music.controller.v1;

import com.example.courses.music.service.VersionService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端版本控制器
 */
@RestController
@RequestMapping("/v1/versions")
public class VersionController {
    private VersionService service;

    @Autowired
    public VersionController(VersionService service) {
        this.service = service;
    }

    /**
     * 检查是否要新版本
     *
     * @return
     */
    @GetMapping("/check")
    public Object check(@RequestParam byte platform, @RequestParam int code) {
        return R.wrap(service.check(platform, code));
    }
}
