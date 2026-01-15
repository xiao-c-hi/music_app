package com.example.courses.music.controller.v1;

import com.example.courses.music.service.TopicService;
import com.example.courses.music.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 话题映射
 */
@RestController
@RequestMapping("/v1/topics")
public class TopicController {
    @Autowired
    private TopicService service;

    /**
     * 话题列表
     *
     * @param since 最后一条数据id；如果为空，就是最新数据；不为空就是查询大于该id的数据
     *              所以要求服务端的id要有大小规律
     *              这种分页的好处是，不会出现重复数据
     * @param size  每页显示多少条
     * @return
     */

    @GetMapping
    public Object index(@RequestParam(required = false) Long since,
                        @RequestParam(defaultValue = "10") int size) {
        //包裹数据
        Object data = R.wrap(service.findAll(since, size));

        //返回数据
        //通过ResponseEntity可以设置很多信息
        return ResponseEntity.ok()
                .header("name", "ixueaedu")
                .body(data);
    }
}

