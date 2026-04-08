package dao.mapper;

import dto.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface BoardMapper {
    String select = "select num, writer, pass, title, content, file1 fileurl, regdate, readcnt, grp, grplevel, grpstep, boardid from board";

    @Select("select ifnull(Max(num), 0) from board")
    int maxNum();

    @Insert("insert into board (num, writer, pass, title, content, file1, boardid, regdate, readcnt, grp, grplevel, grpstep) values (#{num}, #{writer}, #{pass}, #{title}, #{content}, #{fileurl}, #{boardid}, now(), 0, #{grp}, #{grplevel}, #{grpstep})")
    void insert(Board board);

    @Select({"<script>",
            "select count(*) from board where boardid=#{boardId}",
            "<if test='searchType != null and searchContent != null and searchContent != \"\"'> and",
            "(<foreach collection='searchType' item='col' separator='or'>",
            "${col} like concat('%', #{searchContent}, '%')</foreach>)",
            "</if>",
            "</script>"})
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
    @Select({"<script>",
            select + " where boardid=#{boardId} ",
            "<if test='searchType != null and searchContent != null and searchContent != \"\"'> and",
            "(<foreach collection='searchType' item='col' separator='or'>",
            "${col} like concat('%', #{searchContent}, '%')</foreach>)</if>",
            " order by grp desc, grpstep asc limit #{startRow}, #{limit}",
            "</script>"})
    List<Board> selectList(Map<String, Object> param);

    @Select(select + " where num=#{value}")
    Board selectOne(Integer num);

    @Update("update board set readcnt=readcnt+1 where num=#{value}")
    void readCount(Integer num);

    @Update("update board set grpstep=grpstep+1 where grp=#{grp} and grpstep>#{grpstep}")
    void addGrpStep(@Param("grp") int grp,@Param("grpstep") int grpstep);

    @Update("update board set writer=#{writer}, title=#{title}, content=#{content}, file1=#{fileurl} where num=#{num}")
    void update(Board board);

    @Delete("delete from board where num=#{value}")
    void delete(int num);

    /*
     * List<Map<String, Object>> : 1개의 레코드를 Map 생성 하고, 목록을 List로 전달
     * [
     *  {"writer":"홍길동", "cnt":3},
     *  {"writer":"111", "cnt":2},
     *  ...
     * ]
     *
     * board 테이블에서 boardid=2 또는 3 인 데이터 중 글작성자(writer) 별 레코드갯수 (cnt) 조회
     *
     *  writer  cnt → 조회 → Map<String(컬럼명), Object(컬럼의 값)>
     *  홍길동   3             {writer:홍길동, cnt:3}
     *   111     2             {writer:111   , cnt:2}
     */
    @Select("select writer,count(*) cnt from board where boardid=#{value} group by writer order by 2 desc limit 0, 7")
    List<Map<String, Object>> graph1(String id);

    @Select("SELECT date_format(regdate, '%Y-%m-%d') date,COUNT(*) cnt FROM board where boardid=#{value} GROUP BY date_format(regdate, '%Y-%m-%d') ORDER BY 1 LIMIT 0, 7")
    List<Map<String, Object>> graph2(String id);
}
