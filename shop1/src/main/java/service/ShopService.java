package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class ShopService {

    public String sidoSelect1(String si, String gu, HttpServletRequest request) {
        BufferedReader br = null;
        // path : sido.txt 파일의 절대경로
        // request.getServletContext().getRealPath("/") → /webapp/ 폴더내를 의미
        // C:\Dev\spring\workspace\shop1
        String path = request.getServletContext().getRealPath("/") + "file/sido.txt";
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> set = new LinkedHashSet<>(); // 종복불가 + 순서유지 ? LinkedHashSet : TreeSet ?  중복불가 + 정렬
        String data = null;
        if (si == null && gu == null) {
            try {
                while ((data = br.readLine()) != null) {
                    String[] arr = data.split("\\s+");
                    if (arr.length >= 3) set.add(arr[0].trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<String> list = new ArrayList<>(set);
        return list.toString(); // [서울특별시, 경기도, 경상북도, ...]
    }

    public List<String> sidoSelect(String si, String gu, HttpServletRequest request) {
        BufferedReader br = null;
        String path = request.getServletContext().getRealPath("/") + "file/sido.txt";
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> set = new LinkedHashSet<>(); // 중복불가 + 순서유지
        String data = null;
        if (si == null && gu == null) { // sidoSelect1 메서드로 처리.
            return null;
        } else if (gu == null) { // si 파라미터 존재. 시도 선택한 경우. 구군을 검색하여 리턴
            si = si.trim();
            try {
                // br.readLine() : sido.txt 파일에서 한줄씩 읽기
                while ((data = br.readLine()) != null) {
                    String[] arr = data.split("\\s+"); // 공백으로 분리
                    // arr[0].equals(si) : 시도가 si 파라미터값과 같은경우
                    // !arr[1].contains(arr[0]) : 1번째 배열의 값을 2번째 배열의 요소의 값이 같은 값을 포함하는 경우
                    //                  서울특별시 서울특별시 ...
                    if (arr.length >= 3 && arr[0].equals(si) && !arr[1].contains(arr[0])) {
                        set.add(arr[1].trim()); // 구군의 데이터를 set에 추가
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { // si 파라미터, gu 파라미터값 존재.
            si = si.trim();
            gu = gu.trim();
            try {
                while ((data = br.readLine()) != null) {
                    String[] arr = data.split("\\s+");
                    if (arr.length >= 3 && arr[0].equals(si) && arr[1].equals(gu) && !arr[0].equals(arr[1]) && !arr[2].contains(arr[1])) {
                        if (arr.length > 3) {
                            if (arr[3].contains(arr[1])) continue;
                            arr[2] += " " + arr[3];
                        }
                        set.add(arr[2].trim());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<String> list = new ArrayList<>(set); //Set 객체 → List 객체로
        return list;
    }

    public String exchangeString() {
        // https://www.koreaexim.go.kr/wg/HPHKWG057M01
        List<List<String>> trlist = new ArrayList<>();
        String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
        String exdate = null;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements trs = doc.select("tr");
            exdate = doc.select("p.table-unit").html();
            for (Element tr : trs) {
                List<String> tdlist = new ArrayList<>();
                Elements tds = tr.select("td");
                for (Element td : tds) {
                    tdlist.add(td.html());
                }
                if (tdlist.size() > 0) {
                    if (tdlist.get(0).equals("USD")
                            || tdlist.get(0).equals("CNH")
                            || tdlist.get(0).equals("JPY(100)")
                            || tdlist.get(0).equals("EUR")) {
                        trlist.add(tdlist); // tr 태그들 저장
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<p class=text-right>")
                .append(exdate)
                .append("</p>")
                .append("<table class='table table-sm table-bordered'>")
                .append("<tr><th>통화</th><th>기준율</th><th class='text-nowrap'>받으실때</th><th class='text-nowrap'>보내실때</th></tr>");
        for (List<String> tds : trlist) {
            sb.append("<tr><td>")
                    .append(tds.get(0))
                    .append("<br>")
                    .append(tds.get(1))
                    .append("</td><td>")
                    .append(tds.get(4))
                    .append("</td><td>")
                    .append(tds.get(2))
                    .append("</td><td>")
                    .append(tds.get(3))
                    .append("</td></tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    public Map<String, Object> exchangeJson() {
        List<List<String>> trlist = new ArrayList<>();
        String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
        String exdate = null;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements trs = doc.select("tr");
            exdate = doc.select("p.table-unit").html();
            for (Element tr : trs) {
                List<String> tdlist = new ArrayList<>();
                Elements tds = tr.select("td");
                for (Element td : tds) {
                    tdlist.add(td.html());
                }
                if (tdlist.size() > 0) {
                    if (tdlist.get(0).equals("USD")
                            || tdlist.get(0).equals("CNH")
                            || tdlist.get(0).equals("JPY(100)")
                            || tdlist.get(0).equals("EUR")) {
                        trlist.add(tdlist); // tr 태그들 저장
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("exdate", exdate);
        map.put("trlist", trlist);
        return map;
    }

    public String summernoteImageUpload(MultipartFile multipartFile, HttpServletRequest request) {
        File dir = new File(request.getServletContext().getRealPath("/") + "board/image");
        if (!dir.exists()) dir.mkdirs();
        String filesystemName = multipartFile.getOriginalFilename();
        File file = new File(dir, filesystemName);
        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return request.getContextPath() + "/board/image/" + filesystemName;
    }

    public String logoCrawling() {
        String url = "https://gudi.kr/";
        String src = null;
        try {
            Document doc = Jsoup.connect(url).get();
            Element img = doc.selectFirst("img[src='https://cdn.imweb.me/upload/S202407158b5a524da5594/3051209b4c579.png']");
            src = img.attr("src");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return src;
    }

    public String getChatGPTResponse(String question) throws URISyntaxException, IOException, InterruptedException {
        final String API_KEY = ""; // OpenAI에서 제공하는 key 값
        final String ENDPOINT = "https://api.openai.com/v1/chat/completions"; // openAI에 요청하는 URL

        HttpClient client = HttpClient.newHttpClient(); // openAI에 요청할 수 있는 객체
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo"); // gtp의 AI모델 부분 설정
        /*
         * new HashMap(){} → 이름없는 내부객체
         * {             } → 인스턴스 초기화 블럭
         *
         * Map.of → 변경 불가 Map 객체 생성
         * →
         * new HashMap{{
         *      put("role", "system");
         *      put("content", "당신은 자바 전문가 입니다.");
         * }}
         */
        requestBody.put("messages", new Object[]{ // 요청 메시지
                new HashMap<String, String>() {{ //질문내용
                        put("role", "user");
                        put("content", question);
                    }
                },
                Map.of("role", "system", "content", "당신은 자바 전문가 입니다.")
        });
        /*
         * role :
         *  system : 페르소나(정체성) 설정. 대화의 규칙, 맥락 구체화 시킬수 있는 매시지. 대화 시작시 1번. 옵션. 생략 가능
         *  user : 실제 질문. 필수 데이터
         */
        // 자바의 객체를 JSON 형식의 문자열로 변환 할 수 있는 객체 생성
        ObjectMapper objectMapper = new ObjectMapper();
        // requestBody 객체를 json 형식의 문자열로 변경
        // requestBodyJson : {"model":"gpt-3.5-turbo", "messages":[{"role":"user","content":question 값},]}
        //   GPT에 전송한 요청 문자열
        String requestBodyJson = objectMapper.writeValueAsString(requestBody);
        // HTTP 사용될 요청객체 조립하여 객체 완성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(ENDPOINT)) // OpenAI의 URL값. 접속 API의 주소
                .header("Content-Type", "application/json") // 요청 형식은 JSON 형식임을 명시
                .header("Authorization", "Bearer " + API_KEY) // 인증을 위한 API키 설정
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyJson)) // POST 방식으로 요청객체에 설정
                .build();
        // 요청 서버(OpenAi)로 전송
        // HttpResponse.BodyHandlers.ofString() : 응답은 json 형태로 처리하도록 설정. 응답이 올때까지 대기
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // 응답 결과코드 : 200 → 정상처리. GPT 응답 성공
        if (response.statusCode() == 200) {
            // objectMapper.readValue(문자열, 타입) : 문자열을 Map 객체로 생성
            // responseBody : 응답 데이터를 Map 객체로 저장
            Map<String, Object> responseBody = objectMapper.readValue(response.body(), new TypeReference<Map<String, Object>>(){});
            // key 값이 choices인 객체 리턴
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
            Map<String, Object> firstChoice = choices.get(0);
            Map<String, String> message = (Map<String, String>) firstChoice.get("message");
            return message.get("content"); // GPT가 전송한 응답 메시지
        } else { // GPT 응답시 오류 발생
            throw new RuntimeException("API 요청 실패: " + response.body());
        }
    }
}
