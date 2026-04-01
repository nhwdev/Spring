package service;

import dao.ItemDao;
import dao.OrderDao;
import dao.OrderItemDao;
import dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.List;

// Service : Controller와 Repository(Model) 사이의 중간 역할
@Service // @Component + Service 기능
public class ItemService {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;

    public List<Item> itemList() {
        return itemDao.list();
    }

    public Item getItem(Integer id) {
        return itemDao.selectOne(id);
    }

    public void delItem(Integer id) {
        itemDao.delete(id);
    }

    public void crtItem(Item item, HttpServletRequest request) {
        if (item.getPicture() != null & !item.getPicture().isEmpty()) { // 업로드된 파일 존재
            String path = request.getServletContext().getRealPath("/") + "img/"; // 업로드 되는 폴더
            uploadFileCreate(item.getPicture(), path);
            item.setPictureUrl(item.getPicture().getOriginalFilename());
        }
        int maxid = itemDao.maxId(); // 저장된 id의 최대값 조회
        item.setId(maxid + 1);
        itemDao.insert(item);
    }

    public void updItem(@Valid Item item, HttpServletRequest request) {
        if (item.getPicture() != null && !item.getPicture().isEmpty()) {
            String path = request.getServletContext().getRealPath("/") + "img/";
            uploadFileCreate(item.getPicture(), path);
            item.setPictureUrl(item.getPicture().getOriginalFilename());
        }
        itemDao.update(item);
    }

    // 파일 업로드하기
    private void uploadFileCreate(MultipartFile picture, String path) {
        // picture : Item 객체의 MultipartFile 객체. 파일의 내용을 저장
        String orgFile = picture.getOriginalFilename(); // 원본파일 이름
        File f = new File(path);
        if (!f.exists()) f.mkdirs(); // 업로드 폴더가 없는 경우 폴더 생성
        try {
            // pciture의 파일 내용을 path + orgFile 이름의 파일로 이동(저장)
            picture.transferTo(new File(path + orgFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Order orderItem(User loginUser, Cart cart) {
        int maxorderid = orderDao.getMaxOrderId();  // orderid 값의 최대값
        Order order = new Order();                  // Order 객체 생성
        order.setOrderid(maxorderid + 1);           // 최대값 + 1
        order.setUser(loginUser);                   // 로그인한 User 객체 정보
        order.setUserid(loginUser.getUserid());     // 로그인한 userid 정보
        orderDao.insert(order);                     // order 테이블에 저장
        int seq = 0;
        for (ItemSet is : cart.getItemSetList()) {   // 주문상품
            OrderItem orderItem = new OrderItem(order.getOrderid(), ++seq, is);
            order.getItemList().add(orderItem);
            orderItemDao.insert(orderItem); // 주문상품(saleitem) 테이블에 저장
        }
        return order; // 주문정보, 고객정보, 주문상품
    }

    public List<Order> orderList(String userid) {
        // [{orderid:1,userid:0001,...},{orderid:3,userid:0001,...}]
        List<Order> list = orderDao.orderList(userid); // userid가 주문한 order 정보 목록
        for(Order od : list) {
            // orderItemList : orderid에 해당하는 주문상품 목록
            List<OrderItem> orderItemList = orderItemDao.orderList(od.getOrderid()); // 주문번호에 해당하는 주문상품 목록
            for(OrderItem oi : orderItemList) {
                Item item = itemDao.selectOne(oi.getItemid()); //주문상품의 상품정보
                oi.setItem(item); // 상품 정보
            }
            od.setItemList(orderItemList);
        }
        return list;
    }
}
