package com.example.courses.music.controller.v1;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.courses.music.model.Video;
import com.example.courses.music.service.VideoService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 歌单控制器
 */
@RestController
@RequestMapping("/v1/videos")
public class VideoController {

    @Autowired
    VideoService service;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Object index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size
    ) {

        IPage<Video> result = service.findAllDetail(page, size);

        return R.wrap(result);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    public Object show(@PathVariable("id") String id) {
        Video data = service.findDetail(id);

        return R.wrap(data);
    }

}
