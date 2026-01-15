package com.example.courses.music.controller.v1;

import com.example.courses.music.model.Song;
import com.example.courses.music.service.SongService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 歌曲控制器
 */
@RestController
@RequestMapping("/v1/songs")
public class SongController {

    @Autowired
    SongService service;

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    public Object index() {
        List<Song> result = service.findAll();
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
        Song data = service.find(id);
        return R.wrap(data);
    }

}
