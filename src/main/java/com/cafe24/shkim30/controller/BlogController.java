package com.cafe24.shkim30.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"블로그 게시물 정보"})
@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    @PostMapping("/contents")
    public String createBlog() {
        return null;
    }

    @GetMapping("/contents")
    public String readBlog() {
        return null;
    }

    @GetMapping("/contents-list")
    public String readBlogList() {
        return null;
    }

    @PutMapping("/contents")
    public String editBlog() {
        return null;
    }

    @DeleteMapping("/contents")
    public String deleteBlog() {
        return null;
    }
}
