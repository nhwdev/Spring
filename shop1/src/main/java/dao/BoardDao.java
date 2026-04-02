package dao;

import dao.mapper.BoardMapper;
import dto.Board;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDao {
    @Autowired
    private SqlSessionTemplate template;
    private Class<BoardMapper> cls = BoardMapper.class;
    private Map<String, Object> param = new HashMap<>();

    public int maxNum() { // num 컬럼의 최대값 리턴
        return template.getMapper(cls).maxNum();
    }

    public void insert(Board board) { // board 내용을 board 테이블에 저장
        template.getMapper(cls).insert(board);
    }

    public int count(String boardId) {
        param.clear();
        param.put("boardId", boardId);
        return template.getMapper(cls).count(param);
    }

    public List<Board> list(Integer pageNum, int limit, String boardId) {
        param.clear();
        param.put("startRow", (pageNum - 1) * limit);
        param.put("limit", limit);
        param.put("boardId", boardId);
        return template.getMapper(cls).selectList(param);
    }

    public Board detail(int num) {
        return template.getMapper(cls).selectOne(num);
    }

    public void readCount(int num) {
        template.getMapper(cls).readCount(num);
    }
}
