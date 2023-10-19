package com.cafe24.shkim30.repository;

import com.cafe24.shkim30.dto.BlogDTO;
import com.cafe24.shkim30.dto.BlogInsertDTO;
import com.cafe24.shkim30.dto.BlogUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 블로그 게시물 관련 레포지토리
 * @package
 * @author  김석현 < skok1025@naver.com >
 * @since   2022. 07. 08
 * @version 1.0
 */
@Repository
@RequiredArgsConstructor
public class BlogRepository {
    private final SqlSession sqlSession;

    public int saveBlog(BlogInsertDTO blogDTO) {
        return sqlSession.insert("blog.insert", blogDTO);
    }

    public BlogDTO selectBlog(Long no) {
        return sqlSession.selectOne("blog.selectOneByNo", no);
    }

    public List<BlogDTO> selectBlogList(Map<String, Object> searchParam) {
        return  sqlSession.selectList("blog.selectListByCategoryNo", searchParam);
    }

    public int updateBlog(BlogUpdateDTO blogDTO) {
        return sqlSession.update("blog.updateBlog", blogDTO);
    }

    public int deleteBlog(Long no) {
        return sqlSession.delete("blog.deleteBlog", no);
    }
}
