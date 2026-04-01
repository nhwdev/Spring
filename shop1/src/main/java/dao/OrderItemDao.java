package dao;

import dao.mapper.OrderItemMapper;
import dto.OrderItem;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderItemDao {
    @Autowired
    private SqlSessionTemplate template;
    private Class<OrderItemMapper> cls = OrderItemMapper.class;

    public void insert(OrderItem orderItem) {
        template.getMapper(cls).insert(orderItem);
    }

    public List<OrderItem> orderList(int orderid) {
        return template.getMapper(cls).orderList(orderid);
    }
}
