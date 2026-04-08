package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import service.BoardService;
import service.ShopService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/*
 * @Controller : @Component + Controller 기능
 *   @Component : 객체화
 *   Controller : 요청 url에 맞추어 메서드 호출 기능
 *
 *   리턴타입 :
 *     - ModelAndView : 뷰이름 + 전달데이터
 *     - String       : 뷰이름, 데이터 Model 객체에 따로 전송가능
 *
 * @RestController    : @Component + Controller + 클라이언트로 직접 데이터 전송 (뷰 없음)
 *   리턴타입 :
 *     - String : 클라이언트에 전송할 문자열데이터
 *     - Object(Map<DTO>, List<DTO>) : 클라이언트로 직접 객체 전달. JSON 형식으로 전달 (툴 필요!)
 *
 * Sping 4.0 이후에 RestController 기능 추가
 * 이전에는 요청메서드마다 @ResponseBody 기능을 추가하여 사용
 */
@RestController // View 없이 직접 데이터를 클라이언트로 전송
@RequestMapping("ajax")
public class AjaxController {
    @Autowired
    private ShopService service;
    @Autowired
    private BoardService boardService;

    @RequestMapping(value = "select1", produces = "text/plain; charset=utf-8")
    // produces : 클라이언트에 정보 전달
    // text/plain : 문서 형식. MIME타입
    // charset = utf-8 : 한글 인코딩방식 설정
    public String sidoSelect1(String si, String gu, HttpServletRequest request) {
        return service.sidoSelect1(si, gu, request); // 클라이언트로 전달할 문자열 데이터
    }

    // 리턴 타입 : List<String> → 클라이언트에서는 com.fasterxml.jackson.core 를 설정하면 배열로 인식.
    @RequestMapping("select")
    public List<String> select(String si, String gu, HttpServletRequest request) {
        return service.sidoSelect(si, gu, request);
    }

    @RequestMapping(value = "exchangeString", produces = "text/html; charset=utf-8")
    public String exchangeString() {
        return service.exchangeString();
    }

    @RequestMapping(value = "exchangeJson")
    public Map<String,Object> exchangeJson() {
        return service.exchangeJson();
    }

    @RequestMapping(value = "uploadImage", produces="text/plain; charset=utf-8")
    public String summernoteImageUpload(@RequestParam("image")MultipartFile multipartFile, HttpServletRequest request){
        return service.summernoteImageUpload(multipartFile, request);
    }

    @RequestMapping(value = "logoCrawling")
    public String logoCrawling() {
        return service.logoCrawling();
    }

    @RequestMapping("graph1")
    public List<Map.Entry<String, Integer>> graph1(@RequestParam("boardid") String id) {
        Map<String, Integer> map = boardService.graph1(id);
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());
        return list;
    }

    @RequestMapping("graph2")
    public List<Map.Entry<String, Integer>> graph2(@RequestParam("boardid") String id) {
        Map<String, Integer> map = boardService.graph2(id);
        List<Map.Entry<String,Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((a,b) -> a.getKey().compareTo(b.getKey()));
        return list;
    }
}