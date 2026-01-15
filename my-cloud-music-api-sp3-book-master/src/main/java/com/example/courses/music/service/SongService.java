package com.example.courses.music.service;

import com.example.courses.music.mapper.SongMapper;
import com.example.courses.music.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 歌曲服务
 */
@Service
public class SongService {
    @Autowired
    private SongMapper mapper;

    /**
     * 根据id查询
     *
     * @param data
     * @return
     */
    public Song find(String data) {
        Song result = mapper.selectById(data);

        return result;
    }

    /**
     * 创建
     *
     * @param data
     * @return
     */
    public boolean create(Song data) {
        mapper.insert(data);

        return true;
    }

    /**
     * 更新
     *
     * @param data
     */
    public void update(Song data) {
        mapper.updateById(data);
    }

    public List<Song> findAll() {
        List<Song> result = mapper.findAllDetail();

        return result;
    }

    /**
     * 查询歌单下的歌曲
     *
     * @param data
     * @return
     */
    public List<Song> findAllDetailBySheetId(String data) {
        return mapper.findAllDetailBySheetId(data);
    }
}