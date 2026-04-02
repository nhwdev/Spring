package service;

import dao.BoardDao;
import dto.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardDao dao;

    public void boardWrite(Board board, HttpServletRequest request) {
        int maxNum = dao.maxNum(); // board 테이블의 최대 num 컬럼의 값을 리턴
        board.setNum(maxNum + 1);
        board.setGrp(maxNum + 1); // 원글의 경우 grp 컬럼의 값은 num 컬럼의 값과 같음
        if (board.getFile1() != null && board.getFile1().isEmpty()) { // 업로드된 파일 ⭕
            String path = request.getServletContext().getRealPath("/") + "board/file/"; // 업로드 되는 폴더설정
            uploadFileCreate(board.getFile1(), path); // 파일 업로드
            board.setFileurl(board.getFile1().getOriginalFilename()); // 파일 이름 설정
        }
        dao.insert(board); // board 테이블에 게시글 추가
    }

    private void uploadFileCreate(MultipartFile file1, String path) {
        String orgFile = file1.getOriginalFilename();
        File f = new File(path);
        if (!f.exists()) f.mkdirs();
        try {
            file1.transferTo(new File(path + orgFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countBoard(String boardId) {
        return dao.count(boardId);
    }

    public List<Board> listBoard(Integer pageNum, int limit, String boardId) {
        return dao.list(pageNum, limit, boardId);
    }

    public Board detail(int num) {
        return dao.detail(num);
    }

    public void readCount(int num) {
        dao.readCount(num);
    }
}
