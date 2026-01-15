package com.example.courses.music.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.courses.music.exception.CommonException;
import com.example.courses.music.mapper.SheetMapper;
import com.example.courses.music.model.Collect;
import com.example.courses.music.model.Sheet;
import com.example.courses.music.model.Song;
import com.example.courses.music.util.Constant;
import com.example.courses.music.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 歌单服务
 */
@Service
public class SheetService {
    /**
     * 歌单Elastic服务
     */
    @Autowired
    ElasticSheetService elasticSheetService;
    //region 字段
    @Autowired
    private SheetMapper mapper;
    @Autowired
    private SongService songService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CollectService collectService;
    //endregion

    //region 查询

    /**
     * 根据id查询
     *
     * @param data
     * @return
     */
    public Sheet findDetail(String data) {
        Sheet result = mapper.findDetail(data);

        ValidatorUtil.checkExist(result);

        //查询子标签
        tagService.findSubTag(result.getTags());

        //查询该歌单所有音乐
        //也可以通过直接通过sql语句查询
        //但这样太复杂了
        List<Song> datum = songService.findAllDetailBySheetId(data);

        //设置到歌单
        result.setSongs(datum);

        //查询歌单收藏状态
        if (StpUtil.isLogin()) {
            Collect collect = collectService.findBySheetIdAndUserId(data, StpUtil.getLoginIdAsString());
            if (collect != null) {
                result.setCollectId("1");
            }
        }

        return result;
    }

    /**
     * 查询用户创建的歌单
     *
     * @param data
     * @return
     */
    public List<Sheet> findByUserId(String data) {
        LambdaQueryWrapper<Sheet> query = new LambdaQueryWrapper<>();
        query.eq(Sheet::getUserId, data);

        //创建时间倒序
        query.orderByDesc(Sheet::getCreatedAt);

        List<Sheet> result = mapper.selectList(query);

        return result;
    }

    /**
     * 查询用户收藏的歌单
     *
     * @param data
     * @return
     */
    public List<Sheet> findCollectByUserId(String data) {
        return mapper.findCollectByUserId(data);
    }

    /**
     * 根据id,用户id查询
     *
     * @param id
     * @param userId
     * @return
     */
    public Sheet findByIdAndUserId(String id, String userId) {
        LambdaQueryWrapper<Sheet> query = new LambdaQueryWrapper<>();
        query.eq(Sheet::getId, id).eq(Sheet::getUserId, userId);

        return mapper.selectOne(query);
    }

    public IPage<Sheet> findAll(int page, int size) {
        QueryWrapper<Sheet> query = new QueryWrapper<>();

        //创建时间倒序
        query.orderBy(true, false, "sheet.created_at");

        Page<Sheet> pageData = new Page<>(page, size);
        IPage<Sheet> result = mapper.selectPage(pageData, query);

        return result;
    }
    //endregion

    /**
     * 创建
     *
     * @param data
     * @return
     */
    @Transactional
    public boolean create(Sheet data) {
        //保存到数据
        mapper.insert(data);

        //保存标签
        labelService.saveTag(data.getTags(), data.getId(), data.getUserId());

        //更新到es
        elasticSheetService.update(data);

        return true;
    }

    /**
     * 更新
     *
     * @param data
     */
    @Transactional
    public void update(Sheet data) {
        mapper.updateById(data);

        //保存标签
        labelService.saveTag(data.getTags(), data.getId(), data.getUserId());

        //更新到es
        elasticSheetService.update(data);
    }

    /**
     * 删除歌单
     *
     * @param id
     * @param userId
     * @return
     */
    @Transactional
    public int deleteByIdAndUserId(String id, String userId) {
        LambdaQueryWrapper<Sheet> query = new LambdaQueryWrapper<>();
        query.eq(Sheet::getId, id).eq(Sheet::getUserId, userId);

        int delete = mapper.delete(query);
        if (delete < Constant.RESULT_OK) {
            throw new CommonException();
        }

        elasticSheetService.delete(id);

        return delete;
    }
}