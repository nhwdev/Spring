package dao;

import dao.mapper.ShopMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDao {
    @Autowired
    private SqlSessionTemplate template;
    private Class<ShopMapper> cls = ShopMapper.class;

}
