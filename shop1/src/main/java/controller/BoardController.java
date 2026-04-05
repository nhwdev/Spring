package controller;

import dto.Board;
import exception.ShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.BoardService;

import javax.naming.Binding;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("board")
public class BoardController {
    @Autowired
    private BoardService service;

    @GetMapping("*")
    public String getForm(Model model) {
        model.addAttribute(new Board());
        return null;
    }

    @PostMapping("write")
    public String write(@Valid Board board, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        if (board.getBoardid() == null || board.getBoardid().trim().isEmpty()) board.setBoardid("1");
        service.boardWrite(board, request);

        return "redirect:list?boardid=" + board.getBoardid();
    }

    @RequestMapping("list")
    public ModelAndView list(@RequestParam Map<String, String> param, HttpSession session) {
        // @RequestParam : 파라미터값을 Map 객체로 파라미터이름 = 파라미터 값의 형태로 전달
        Integer pageNum = null;
        // param.keySet() : 파라미터 이름 목록
        for (String key : param.keySet()) {
            if (param.get(key) == null || param.get(key).trim().isEmpty()) {
                param.put(key, null);
            }
        }
        if (param.get("pagenum") != null) {
            pageNum = Integer.parseInt(param.get("pagenum"));
        } else { // pagenum 파라미터가 없는 경우
            pageNum = 1;
        }
        String boardId = param.get("boardid");
        if (boardId == null) boardId = "1";
        String searchType = param.get("searchtype");
        String searchContent = param.get("searchcontent");

        ModelAndView mav = new ModelAndView();
        String boardName = null;
        switch (boardId) {
            case "1":
                boardName = "공지사항";
                break;
            case "2":
                boardName = "자유게시판";
                break;
            case "3":
                boardName = "도움말";
                break;
        }
        int limit = 10; // 화면에 출력될 게시물 건수
        int countList = service.countBoard(boardId, searchType, searchContent); // 게시판 종류별, 검색 내용으로 전체 등록된 게시물 건수
        List<Board> listBoard = service.listBoard(pageNum, limit, boardId, searchType, searchContent); // 화면에 출력할 게시물 목록
        // int maxPage = (int) ((double) countList / limit + 0.95); // 최대페이지
        int maxPage = (int) Math.ceil((double) countList / limit);
        /*
         * countList : 3
         *  (int)((double)3/10 + 0.95) = (int)1.25 → 1
         *
         * countList : 31
         *  (int)((double)31/10 + 0.95 = (int) 4.05 → 4
         *
         * countList : 40
         *  (int)((double)40/10 + 0.95 = (int) 4.95 → 4
         *
         * countList : 501
         *  (int)((double)501/10 + 0.95 = (int) 51.05 → 51
         */
        int startPage = (int) ((pageNum / 10.0 + 0.9) - 1) * 10 + 1;
        /*
         * 현재 페이지 : 1 → 1 ~ 10
         *  1 / 10.0 → 0.1 → 0.1 + 0.9 → 1.0 - 1 → (int) (0.0) → 0 * 10 → 0 + 1 → 1
         *
         * 현재 페이지 : 5 → 1 ~ 10
         *  5 / 10.0 → 0.5 → 0.5 + 0.9 → 1.4 - 1 → (int) (0.4) → 0 * 10 → 0 + 1 → 1
         *
         * 현재 페이지 : 10 → 1 ~ 10
         *  10 / 10.0 → 1.0 → 1.0 + 0.9 → 1.9 - 1 → (int) (0.9) → 0 * 10 → 0 + 1 → 1
         *
         * 현재 페이지 : 11 → 11 ~ 20
         *  11 / 10.0 → 1.1 → 1.1 + 0.9 → 2.0 - 1 → (int) (1.0) → 1 * 10 → 10 + 1 → 11
         *
         * 현재 페이지 : 15 → 11 ~ 20
         *  15 / 10.0 → 1.5 → 1.5 + 0.9 → 2.4 - 1 → (int) (1.4) → 1 * 10 → 10 + 1 → 11
         */
        int endPage = startPage + 9;
        if (endPage > maxPage) endPage = maxPage;
        mav.addObject("date", new Date());
        mav.addObject("boardid", boardId);      // 게시판 종류
        mav.addObject("boardname", boardName);  // 게시판 종류 이름
        mav.addObject("pagenum", pageNum);      // 현재 페이지 번호
        mav.addObject("maxpage", maxPage);      // 최대 페이지
        mav.addObject("startpage", startPage);  // 화면에 출력된 시작 페이지
        mav.addObject("endpage", endPage);      // 화면에 출력된 종료 페이지
        mav.addObject("countlist", countList);  // 전체 등록된 게시물 건수
        mav.addObject("listboard", listBoard);  // 출력할 게시물 목록
        int boardNo = countList - (pageNum - 1) * limit;
        /*
         * 현재페이지 1. 게시글 수 : 21
         *      21 - 0 * 10 : 21
         * 현재페이지 2. 게시글 수 : 21
         *      21 - (2 - 1) * 10 : 11
         */
        mav.addObject("boardno", boardNo);      // 화면에 보여질 게시물 번호의 시작 값
        return mav;
    }

    @GetMapping("detail")
    public String detail(Integer num, Model model) {
        Board board = service.detail(num); // num의 게시물 조회
        service.readCount(num); // 조회수 증가!
        model.addAttribute("board", board);
        return null; // /WEB-INF/view/board/detail.jsp 요청
    }

    @GetMapping({"reply", "update", "delete"})
    public String getBoard(Integer num, Model model) {
        Board board = service.detail(num);
        model.addAttribute("board", board);
        return null;
    }

    /*
     * order by grp desc, grpstep asc
     *
     *        num  grp  grplevel  grpstep
     * 원글   3    3    0         0
     * 원글   2    2    0         0
     * 대댓글 6    2    1         1
     * 대댓글 7    2    2         2
     * 댓글   4    2    1         3
     * 원글   1    1    0         0
     * 댓글   5    1    1         1
     *
     * 1. 유효성 검사하기-파라미터값 저장
     *  - 원글 정보 : num, grp, grp, grplevel, grpstep, boardid
     *  - 답글 정보 : writer, pass, title, content
     * 2. DB에 insert → service.boardReply()
     *  - 원글의 grpstep 보다 큰 기존 등록된 답글의 grpstep 값을 +1 수정
     *    → boardDao.grpStepAdd()
     *  - num : maxNum() + 1
     *  - DB에 insert → boardDao.insert()
     *    grp : 원글과 동일
     *    grplevel : 원글의 grplevel + 1
     *    grpstep : 원글의 grpstep + 1
     * 3. 등록 성공 : list 페이지 이동
     *    등록 실패 : "답변 등록시 오류 발생" reply 페이지 이동
     */
    @PostMapping("reply")
    public String reply(@Valid Board board, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> map = bindingResult.getModel();
            Board b = (Board) map.get("board"); // 화면에서 입력바든 값을 저장한 Board 객체
            b.setTitle(board.getTitle().substring(3)); // 원글의 제목으로 변경
            model.addAllAttributes(bindingResult.getModel());
            return null;
        }
        try {
            service.replyBoard(board);
            return "redirect:list?boardid=" + board.getBoardid();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ShopException("답변등록시 오류 발생", "reply?num=" + board.getNum());
        }
    }

    /*
     * 1. 유효성 검사하기 - 파라미터값 저장
     * 2. 비밀번호 검증
     * 3. DB에 update → BoardService.boardReply()
     *  - DB에 update → BoardDao.update()
     * 4. 변경 성공 : list 페이지 이동
     *    변경 실패 : "게시글 수정시 오류 발생" update 이동
     */
    @PostMapping("update")
    public String update(@Valid Board board, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        Board dbBoard = service.detail(board.getNum());
        // board.getpass() : 입력된 비밀번호
        // dbBoard.getpass() : DB에 등록된 비밀번호
        if (!board.getPass().equals(dbBoard.getPass())) {
            throw new ShopException("비밀번호가 틀립니다", "update?num=" + board.getNum());
        }
        try {
            // 1. DB의 내용을 등록된 내용으로 변경 : writer, title, content, file1
            // 2. file 업로드
            service.updateBoard(board, request);
            return "redirect:list";
        } catch (Exception e) {
            e.printStackTrace();
            throw new ShopException("게시글 수정에 실패했습니다.", "update?num=" + board.getNum());
        }
    }

    /*
     * 1. 비밀번호가 일치하면 num 해당하는 게시물 삭제.
     *    비밀번호 오류시 globalError 방식으로 처리하기
     * 2. BoardService.boardDelete
     *    boardDao.delete 메서드 명으로 처리하기
     */
    @PostMapping("delete")
    public String delete(Board board, BindingResult bindingResult) {
        if (board.getPass() == null || board.getPass().trim().isEmpty()) {
            bindingResult.reject("error.required.password");
            return null;
        }
        Board dbBoard = service.detail(board.getNum());
        if (!board.getPass().equals(dbBoard.getPass())) {
            bindingResult.reject("error.login.password");
            return null;
        }
        try {
            service.deleteBoard(board.getNum());
            return "redirect:list?boardid=" + dbBoard.getBoardid();
        } catch (Exception e) {
            bindingResult.reject("error.update.user");
            return null;
        }
    }
}