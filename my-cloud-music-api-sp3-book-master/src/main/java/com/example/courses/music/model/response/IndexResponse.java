package com.example.courses.music.model.response;

import com.example.courses.music.model.Ad;
import com.example.courses.music.model.Base;
import com.example.courses.music.model.Button;
import com.example.courses.music.model.Product;
import com.example.courses.music.model.*;

import java.util.Arrays;
import java.util.List;


public class IndexResponse extends Base {

    /**
     * 轮播图
     */
    private List<Ad> banners;
    /**
     * 快捷按钮
     */
    private List<Button> buttons;

    /**
     * 大轮播图
     */
    private List<String> larges = Arrays.asList(
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/16305100fd7913dd727e8906b97e8998.png",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/f8a28a90c3c449ba422a10e40085d2e9.png",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/200a6f45d1ccbf7d8fd92f4bbe2842d4.jpeg"
    );

    private  String banner1="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/f4c4a11536a720ee0f1b516e53e4d868.jpg";

    /**
     * 热门商品
     */
    private List<Product> hots;

    /**
     * 最新商品
     */
    private List<Product> news;

    public List<Ad> getBanners() {
        return banners;
    }

    public void setBanners(List<Ad> banners) {
        this.banners = banners;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public String getBanner1() {
        return banner1;
    }

    public void setBanner1(String banner1) {
        this.banner1 = banner1;
    }

    public List<Product> getHots() {
        return hots;
    }

    public void setHots(List<Product> hots) {
        this.hots = hots;
    }

    public List<Product> getNews() {
        return news;
    }

    public void setNews(List<Product> news) {
        this.news = news;
    }

    public List<String> getLarges() {
        return larges;
    }

    public void setLarges(List<String> larges) {
        this.larges = larges;
    }
}
