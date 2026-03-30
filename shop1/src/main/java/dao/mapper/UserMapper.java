package dao.mapper;

import dto.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    @Insert("insert into useraccount (userid, username, password, phoneno, postcode, address, email, birthday) values (#{userid}, #{username}, #{password}, #{phoneno}, #{postcode}, #{address}, #{email}, #{birthday})")
    void insert(User user);

    @Select("select * from useraccount where userid=#{value}")
    User selectOne(String userid);

    @Update("update useraccount set username=#{username}, phoneno=#{phoneno}, postcode=#{postcode}, address=#{address}, email=#{email}, birthday=#{birthday} where userid=#{userid}")
    void update(User user);

    @Delete("delete from useraccount where userid=#{value};")
    void delete(String userid);

    @Update("update useraccount set password=#{pass} where userid=#{userid}")
    void pwUser(@Param("userid") String userid,@Param("pass") String pass);

    @Select({"<script>",
            "select ${col} from useraccount where email=#{email} and phoneno=#{phoneno}",
            "<if test='userid != null'> and userid=#{userid}</if>",
            "</script>"})
    String search(Map<String, Object> param);
    /*
     * userids = [test1, test2] 아이디 조회
     * select * from useraccount where userid in ('test1','test2')
     */
    @Select({"<script>",
             "select * from useraccount",
             "<if test='userids != null'> where userid in <foreach collection='userids' item='id' separator=',' open='(' close=')'>#{id}</foreach></if>",
             "</script>"})
    List<User> selectList(Map<String, Object> param);
}
