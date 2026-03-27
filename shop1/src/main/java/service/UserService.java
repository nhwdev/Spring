package service;

import dao.UserDao;
import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@Service
public class UserService {
    @Autowired
    private UserDao dao;

    public void userInsert(User user) {
        dao.insert(user);
    }

    public User getUser(String userid) {
        return dao.selectOne(userid);
    }

    public void updUser(User user) {
        dao.update(user);
    }

    public void delUser(String userid) {
        dao.delete(userid);
    }

    public void pwUser(String userid, String pass) {
        dao.update(userid, pass);
    }
}
