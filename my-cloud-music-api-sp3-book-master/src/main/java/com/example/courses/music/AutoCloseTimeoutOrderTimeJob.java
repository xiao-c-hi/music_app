package com.example.courses.music;

import com.example.courses.music.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定关闭订单任务
 * <p>
 * 这种方式适合用户量小的情况
 * 如果有一分钟有几百订单量，可能处理不过来
 * 需要使用队列实现
 */
@Component
public class AutoCloseTimeoutOrderTimeJob {
    /**
     * 日志api
     */
    private final static Logger log = LoggerFactory.getLogger(AutoCloseTimeoutOrderTimeJob.class);

    @Autowired
    OrderService service;

    /**
     * 每分钟执行一次
     * <p>
     * fixedRate:固定毫秒执行
     * 执行时间还有cron写法，和Linux下定时任务写法差不多
     * <p>
     * 思路：查询从下单时间计算，超过16分钟的订单，然后关闭订单
     * 如果有优惠券，并将优惠券标记为未使用
     * 如果还涉及到商品库存，要加上购买的数量
     * <p>
     * 多加1分钟是，防止用户支付了，但还没有回调就关闭了
     *
     * 注意：这种实现不一定准确，可能有1，2分钟延迟，如果要准确可以用队列实现
     */
    @Scheduled(fixedRate = 60000)
    public void run() {
        log.info("run {}", new Date());

        service.closeTimeoutOrder();
    }
}
