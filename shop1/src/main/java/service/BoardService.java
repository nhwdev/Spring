package service;

import dao.BoardDao;
import dto.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
        if (board.getFile1() != null && !board.getFile1().isEmpty()) { // 업로드된 파일 ⭕
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

    public int countBoard(String boardId, String searchType, String searchContent) {
        return dao.count(boardId, searchType, searchContent);
    }

    public List<Board> listBoard(Integer pageNum, int limit, String boardId, String searchType, String searchContent) {
        return dao.list(pageNum, limit, boardId, searchType, searchContent);
    }

    public Board detail(Integer num) {
        return dao.detail(num);
    }

    public void readCount(Integer num) {
        dao.readCount(num);
    }

    public void replyBoard(Board board) {
        dao.addGrpStep(board); // grp 내의 기존의 원글보다 큰 값을 가진 grpstep의 값을 1 증가
        // 답글의 내용을 DB에 등록
        int max = dao.maxNum(); // board 테이블에서 num 값의 최대값
        board.setNum(max+1); // 추가될 게시글의 num 값을 설정
        board.setGrplevel(board.getGrplevel() + 1); // 원글 + 1
        board.setGrpstep(board.getGrpstep() + 1); // 원글 아래에 출력되도록 설정
        // 원글의 grp, boardid 값은 그대로 유지
        dao.insert(board);
    }

    public void updateBoard(Board board, HttpServletRequest request) {
        // 첨부파일 업로드
        if (board.getFile1() != null && !board.getFile1().isEmpty()) { // 첨부파일이 수정된 경우.
            String path = request.getServletContext().getRealPath("/") + "board/file/";
            uploadFileCreate(board.getFile1(), path);
            board.setFileurl(board.getFile1().getOriginalFilename()); //첨부 파일이름 fileUrl 프로퍼티 값 변경
        }
        dao.update(board);
    }

    public void deleteBoard(int num) {
        dao.delete(num);
    }
}
