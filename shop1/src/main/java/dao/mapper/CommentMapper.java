package dao.mapper;

import dto.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {

    @Select("select ifnull(max(seq),0) from comment where num=#{value}")
    int maxSeq(int num);

    @Insert("insert into comment (num, seq, writer, pass, content, regdate) values (#{num}, #{seq}, #{writer}, #{pass}, #{content}, now())")
    void insert(Comment comment);

    @Select("select * from comment where num=#{value} order by seq desc")
    List<Comment> list(Integer num);

    @Select("select * from comment where num=#{num} and seq=#{seq}")
    Comment getComment(@Param("num") int num, @Param("seq") int seq);

    @Delete("delete from comment where num=#{num} and seq=#{seq}")
    void delete(@Param("num") int num, @Param("seq") int seq);

}
