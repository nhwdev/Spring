package dao;

import dao.mapper.CommentMapper;
import dto.Comment;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDao {
    @Autowired
    private SqlSessionTemplate template;
    private Map<String, Object> param = new HashMap<>();
    private Class<CommentMapper> cls = CommentMapper.class;

    public int maxSeq(int num) {
        return template.getMapper(cls).maxSeq(num);
    }

    public void insert(Comment comment) {
        template.getMapper(cls).insert(comment);
    }

    public List<Comment> list(Integer num) {
        return template.getMapper(cls).list(num);
    }

    public Comment getComment(int num, int seq) {
        return template.getMapper(cls).getComment(num, seq);
    }

    public void delete(int num, int seq) {
        template.getMapper(cls).delete(num, seq);
    }

}
