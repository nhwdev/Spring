package controller;

import dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller // @Component(객체화 시킴) + Controller 기능
@RequestMapping("item") // https://localhost:8080/shop1/item/**
public class ItemController {
    @Autowired
    private ItemService service;

    // http://localhost:8080/shop1/item/list
    @RequestMapping("list") // GET, POST 방식 상관없이 호출
    public ModelAndView list() {
        /*
         * ModelAndView : Model(클라이언트로 전송할 객체) + View(화면)
         */
        ModelAndView mav = new ModelAndView();
        // itemList : item 테이블의 모든 데이터 저장
        List<Item> itemList = service.itemList();
        mav.addObject("itemList", itemList);
        // view가 설정되지 않으면 url과 같은 view를 호출함 : /item/list
        return mav;
    }

    // http://localhost:8080/shop1/item/update?id=1
    @GetMapping({"detail", "delete", "update"}) // GET 방식 요청
    public ModelAndView detail(Integer id) { // id 파리미터값을 id 매개변수에 저장
        ModelAndView mav = new ModelAndView();
        Item item = service.getItem(id);
        mav.addObject("item", item); // view 이름 지정하지 않으면 url 정보와 같은 view 선택
        return mav;
    }

    /*
     * 1. id 파라미터 존재.
     * 2. database에서 id 해당하는 레코드를 삭제
     * 3. list 를 재요청
     */
    @PostMapping("delete")  // POST 방식 요청
    public String delete(Integer id) { // id 파라미터값을 id 매개변수로 저장
        ModelAndView mav = new ModelAndView();
        service.delItem(id);
        return "redirect:list"; // https://localhost:8080/shop1/item/list 재요청
//        mav.setViewName("redirect:/item/list");
//        return mav;
    }

    @GetMapping("create")
    public ModelAndView createForm() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(new Item());
        return mav;
    }

    @PostMapping("create") // Post 방식 요청
    public ModelAndView create(@Valid Item item, BindingResult bindingResult, HttpServletRequest request) {
        /*
         * Item 객체에 파라미터 정보와, 파일정보 저장
         * 파라미터 값과 Item 객체의 프로퍼티 값을 비교하여 저장
         */
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) { // @Valid에서 등록된 오류가 존재?
            return mav;
        }
        // 입력값 검증 완료시 실행
        service.crtItem(item, request);
        mav.setViewName("redirect:list");
        return mav;
    }

    @PostMapping("update")
    public ModelAndView update(@Valid Item item, BindingResult bindingResult, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        if(bindingResult.hasErrors()){
            return mav;
        }
        service.updItem(item, request);
        mav.setViewName("redirect:detail?id=" + item.getId());
        return mav;
    }
}