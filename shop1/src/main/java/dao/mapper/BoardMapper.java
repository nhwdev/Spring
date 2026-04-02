package dao.mapper;

import dto.Board;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface BoardMapper {
    String select = "select num, writer, pass, title, content, file1 fileurl, regdate, readcnt, grp, grplevel, grpstep, boardid from board";

    @Select("select ifnull(Max(num), 0) from board")
    int maxNum();

    @Select("insert into board (num, writer, pass, title, content, file1, boardid, regdate, readcnt, grp, grplevel, grpstep) values (#{num}, #{writer}, #{pass}, #{title}, #{content}, #{fileurl}, #{boardid}, now(), 0, #{grp}, #{grplevel}, #{grpstep})")
    void insert(Board board);

    @Select("select count(*) from board where boardid=#{boardId}")
    int count(Map<String, Object> param);
    /*
     *        시작인덱스    갯수
     * limit #{startRow}, #{limit}  : 조회된 레코드 중 일부만 리턴 → MySQL, MariaDB 사용가능 예약어
     * 1페이지 :   0    ,     10   → 1번째 레코드에서 10개만 리턴
     * 2페이지 :  10    ,     10   → 11번째 레코드에서 10개만 리턴
     * 3페이지 :  20    ,     10   → 21번째 레코드에서 10개만 리턴
     *
     * 오라클 : rownum → 레코드의 조회되는 순서를 의미하는 예약어
     */
    @Select(select + " where boardid=#{boardId} order by grp desc, grpstep asc limit #{startRow}, #{limit}")
    List<Board> selectList(Map<String, Object> param);

    @Select(select + " where num=#{value}")
    Board selectOne(int num);

    @Update("update board SET readcnt = readcnt + 1 WHERE num = #{value}")
    void readCount(int num);
}
