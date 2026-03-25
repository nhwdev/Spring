package service;

import dao.ItemDao;
import dto.Item;
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
}
