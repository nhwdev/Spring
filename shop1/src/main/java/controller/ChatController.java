package controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("chat")
public class ChatController {

    @RequestMapping("*")
    public String getForm() {
        return null;
    }

    @PostMapping("naversearch")
    @ResponseBody // view 없이 직접 데이터를 클라이언트로 전송
    public JSONObject naversearch(String data, Integer display, Integer start, String type) {
        String clientID = "0EA31YQT2my9OMMRgYlI";
        String clientSecret = "Q7VSuVV9Qz";
        StringBuilder json = new StringBuilder();
        /*
         * 100건의 데이터가 존재 :
         *    start    cnt
         *      1       1
         *      2       11
         *      3       21
         *
         * 네이버에 검색요청시 페이지로 검색하지 않고, 조회되는 검색 레코드의 순서로 조회
         */
        int cnt = (start - 1) * display + 1;
        String text = URLEncoder.encode(data, StandardCharsets.UTF_8); // 검색 내용
        try {
            String apiURL = "https://openapi.naver.com/v1/search/" + type + ".json?query=" + text + "&display=" + display + "&start=" + cnt;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientID);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8));
            }

            String inputLine = null;
            while ((inputLine = br.readLine()) != null) {
                json.append(inputLine);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = null;
        try {
            // json : 네이버에서 검색한 내용
            jsonObj = (JSONObject) parser.parse(json.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }
}
