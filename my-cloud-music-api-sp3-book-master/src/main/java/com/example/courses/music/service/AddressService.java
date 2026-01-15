package com.example.courses.music.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.courses.music.mapper.AddressMapper;
import com.example.courses.music.model.Address;
import com.example.courses.music.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地址服务
 */
@Service
public class AddressService {
    @Autowired
    private AddressMapper mapper;

    /**
     * 根据用户id查询所有
     *
     * @param data
     * @return
     */
    public List<Address> findAllByUserId(String data) {
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUserId, data);
        queryWrapper.orderByDesc(Address::getUpdatedAt);
        return mapper.selectList(queryWrapper);
    }

    /**
     * 根据id和用户id查询
     *
     * @param id
     * @param userId 添加用户id目的是，防止恶意用户能使用其他人的地址
     * @return
     */
    public Address findByIdAndUserId(String id, String userId) {
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUserId, userId).eq(Address::getId, id);
        return mapper.selectOne(queryWrapper);
    }

    /**
     * 添加
     *
     * @param data
     * @return
     */
    public int create(Address data) {
        updateAllNotDefault(data);

        return mapper.insert(data);
    }

    /**
     * 更新
     *
     * @param data
     * @return
     */
    public int update(Address data) {
        updateAllNotDefault(data);

        return mapper.updateById(data);
    }

    private int updateAllNotDefault(Address data) {
        if (data.isDefault()) {
            //如果对象是默认

            //那就把该用户其他所有地址都改成不是默认的
            UpdateWrapper<Address> queryWrapper = new UpdateWrapper<>();
            queryWrapper.eq("user_id", data.getUserId());
            queryWrapper.set("`default`", 0);
            return mapper.update(null, queryWrapper);
        }

        return 0;
    }

    /**
     * 删除
     *
     * @param id
     * @param userId
     * @return
     */
    public int deleteByIdAndUserId(String id, String userId) {
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUserId, userId).eq(Address::getId, id);
        return mapper.delete(queryWrapper);
    }

    /**
     * 查询用户默认地址
     *
     * @param userId
     * @return
     */
    public Address findByUserIdAndDefault(String userId) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("`default`", Constant.VALUE10);
        return mapper.selectOne(queryWrapper);
    }
}