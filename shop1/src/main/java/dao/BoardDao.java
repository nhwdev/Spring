package dao;

import dao.mapper.BoardMapper;
import dto.Board;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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

    public int count(String boardId, String searchType, String searchContent) {
        param.clear();
        List<String> list = Arrays.asList("title", "writer", "content");
        List<String> searchTypes = (searchType == null || searchType.isEmpty())
                ? list : Arrays.stream(searchType.split(","))
                         .filter(list::contains).collect(Collectors.toList());
        param.put("searchType", searchTypes);
        param.put("boardId", boardId);
        param.put("searchContent", searchContent);
        return template.getMapper(cls).count(param);
    }

    public List<Board> list(Integer pageNum, int limit, String boardId, String searchType, String searchContent) {
        param.clear();
        param.put("startRow", (pageNum - 1) * limit);
        param.put("limit", limit);
        param.put("boardId", boardId);
        List<String> list = Arrays.asList("title", "writer", "content");
        List<String> searchTypes = (searchType == null || searchType.isEmpty())
                ? list : Arrays.stream(searchType.split(","))
                         .filter(list::contains).collect(Collectors.toList());
        param.put("searchType", searchTypes);
        param.put("searchContent", searchContent);
        return template.getMapper(cls).selectList(param);
    }

    public Board detail(Integer num) {
        return template.getMapper(cls).selectOne(num);
    }

    public void readCount(Integer num) {
        template.getMapper(cls).readCount(num);
    }

    public void addGrpStep(Board board) {
        template.getMapper(cls).addGrpStep(board.getGrp(), board.getGrpstep());
    }

    public void update(Board board) {
        template.getMapper(cls).update(board);
    }

    public void delete(int num) {
        template.getMapper(cls).delete(num);
    }

    public List<Map<String, Object>> graph1(String id) {
        return template.getMapper(cls).graph1(id);
    }

    public List<Map<String, Object>> graph2(String id) {
        return template.getMapper(cls).graph2(id);
    }
}
