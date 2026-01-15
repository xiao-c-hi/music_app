package com.example.courses.music.controller.v1;

import com.example.courses.music.service.RedisStringService;
import com.example.courses.music.service.UserService;
import com.example.courses.music.util.CacheKeyUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 图片验证码控制器
 */
@Controller
@RequestMapping("/codes")
public class CodeImageController {
    private static Logger log = LoggerFactory.getLogger(CodeImageController.class);

    /**
     * 用户服务
     */
    @Autowired
    private UserService userService;

    /**
     * 用户服务
     */
    @Autowired
    private RedisStringService redisStringService;

    /**
     * 图片验证码
     *
     * @param request
     * @param response
     */
    @GetMapping("/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) {
        try {
//            //1.在web网站中使用，他会自动存入session
//            //使用Gif验证码
//            GifCaptcha captcha = new GifCaptcha(130, 38, 6);
//            CaptchaUtil.out(captcha, request, response);
//
//            //2.验证验证码
//            if (!CaptchaUtil.ver("用户输入的验证码", request)) {
//                CaptchaUtil.clear(request);  // 清除session中的验证码
//                //验证码不正确
//            }

            //因为是API应用，所以不使用session，不使用他提供的工具类，验证码保存到数据
            // 设置请求头为输出图片类型
            response.setContentType("image/gif");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            // 三个参数分别为宽、高、位数
            SpecCaptcha specCaptcha = new SpecCaptcha(150, 48, 6);

            // 设置字体
//            specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置

            // 设置类型，纯数字、纯字母、字母数字混合
            specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

            // 验证码存入redis，可以使用自动失效，当然也可以存入数据库
            //保存规则，key=codes/texts/验证码，值就就设置为0，没有任何意义，1分钟有效
            redisStringService.update(CacheKeyUtil.codeDataKey(specCaptcha.text().toLowerCase()), "0", 1, TimeUnit.MINUTES);

            // 输出图片流
            specCaptcha.out(response.getOutputStream());
        } catch (IOException e) {
            log.error("imageCode error {}", e);
        }
    }
}
