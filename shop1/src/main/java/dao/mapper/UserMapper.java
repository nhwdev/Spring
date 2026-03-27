package dao.mapper;

import dto.User;
import org.apache.ibatis.annotations.*;

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
}
