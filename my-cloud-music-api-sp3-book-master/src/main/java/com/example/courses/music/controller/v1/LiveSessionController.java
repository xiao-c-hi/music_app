package com.example.courses.music.controller.v1;

import com.example.courses.music.model.request.LiveTokenRequest;
import com.example.courses.music.service.LiveService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 直播登录退出控制器
 */
@RestController
@RequestMapping("/v1/sessions/live")
public class LiveSessionController {
    @Autowired
    private LiveService service;

    /**
     * 生成直播token
     * @param data
     * @return
     */
    @PostMapping
    public @ResponseBody
    Object create(@RequestBody LiveTokenRequest data) {
        return R.wrap(service.create(data));
    }
}
