package dao.mapper;

import dto.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderItemMapper {

    @Insert("insert into orderitem (orderid, seq, itemid, quantity) values (#{orderid}, #{seq}, #{itemid}, #{quantity})")
    void insert(OrderItem orderItem);

    @Select("select * from orderitem where orderid=#{value}")
    List<OrderItem> orderList(int orderid);
}
