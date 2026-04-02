package controller;

import dto.Board;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        int countList = service.countBoard(boardId); // 게시판 종류별 전체 등록된 게시물 건수
        List<Board> listBoard = service.listBoard(pageNum, limit, boardId); // 화면에 출력할 게시물 목록
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

    @RequestMapping("detail")
    public ModelAndView detail(@RequestParam Map<String, String> param, HttpSession session){
        for (String key : param.keySet()) {
            if (param.get(key) == null || param.get(key).trim().isEmpty()) {
                param.put(key, null);
            }
        }
        int num = Integer.parseInt(param.get("num"));
        Board board = service.detail(num);
        service.readCount(num);
        ModelAndView mav = new ModelAndView();
        mav.addObject("board", board);
        return mav;
    }
}
