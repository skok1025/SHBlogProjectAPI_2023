package com.cafe24.shkim30.service;

import com.cafe24.shkim30.dto.BlogDTO;
import com.cafe24.shkim30.dto.BlogInsertDTO;
import com.cafe24.shkim30.dto.BlogUpdateDTO;
import com.cafe24.shkim30.library.libEncrypt;
import com.cafe24.shkim30.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 블로그 게시물 관련 서비스
 * @package
 * @author  김석현 < skok1025@naver.com >
 * @since   2022. 07. 08
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    @Transactional
    public BlogInsertDTO addBlog(BlogInsertDTO blogDTO) {
        int result = blogRepository.saveBlog(blogDTO);

        return result > 0 ? blogDTO : null;
    }

    public BlogDTO getBlog(Long no) {
        BlogDTO blogDTO = blogRepository.selectBlog(no);

        if (blogDTO != null) {
            blogDTO.setMember_name(libEncrypt.decrypt_AES(blogDTO.getMember_name().getBytes(), libEncrypt.AES_KEY.getBytes()));
        }

        return blogDTO;
    }

    public List<BlogDTO> getBlogList(Long categoryNo, Long pageContentSize, Long startIndex) {
        Map<String, Long> searchParam = new HashMap<>();
        searchParam.put("category_no", categoryNo);
        searchParam.put("page_content_size", pageContentSize);
        searchParam.put("start_index", startIndex);

        return blogRepository.selectBlogList(searchParam);
    }

    public int editBlog(BlogUpdateDTO blogDTO) {
        return blogRepository.updateBlog(blogDTO);
    }

    public int deleteBlog(Long no) {
        return blogRepository.deleteBlog(no);
    }
}
