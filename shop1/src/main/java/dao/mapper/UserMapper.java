package dao.mapper;

import dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Insert("insert into useraccount (userid, username, password, phoneno, postcode, address, email, birthday) values (#{userid}, #{username}, #{password}, #{phoneno}, #{postcode}, #{address}, #{email}, #{birthday})")
    void insert(User user);

    @Select("select * from useraccount where userid=#{value}")
    User selectOne(String userid);
}
