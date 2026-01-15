package com.example.courses.music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.courses.music.model.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 歌曲映射
 */
@Repository
public interface SongMapper extends BaseMapper<Song> {
    /**
     * 列表，会查询关联数据
     *
     * @return
     */
    List<Song> findAllDetail();

    /**
     * 查询歌单下的歌曲
     *
     * @param data
     * @return
     */
    List<Song> findAllDetailBySheetId(String data);
}
