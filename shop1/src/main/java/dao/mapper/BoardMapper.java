package dao.mapper;

import dto.Board;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
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

    @Select({"<script>",
            "select count(*) from board where boardid=#{boardId}",
            "<if test='searchType != null'>and ${searchType} like '%${searchContent}%'</if>",
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
                    "<if test='searchType != null'>and ${searchType} like '%${searchContent}%'</if>",
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
}
