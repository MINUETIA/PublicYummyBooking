package kr.co.jhta.web.service;

import kr.co.jhta.web.dto.Coordinates;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AddressService {
    /*
     카카오 디벨로퍼 API에서 로컬 요청을 위해서는 아래와 같은 양식으로 HTTP 요청을 보내야 한다.
        curl -v -X GET "https://dapi.kakao.com/v2/local/search/address.json" \
        -H "Authorization: KakaoAK ${REST_API_KEY}" \
        --data-urlencode "query=전북 삼성동 100"
     해당 요청을 만들기 위해서 Spring에서는 아래와 같이 코드를 작성하는 것.
    */
    // URI 생성
    // Kakao developers에 API를 요청하기 위한 URI는 정해져있다. 이것을 필드로 설정한다.
    private final String uri = "https://dapi.kakao.com/v2/local/search/address.json";

    /*
    앱 키 가져오기
        API를 요청하기 위해서는 Kakao developer에서 발급한 앱키를 Authorization 헤더에 넣어서 HTTP 요청해야한다.
        이 키를 .env에 저장해두고 application.properties를 통해 @Value 애노테이션을 사용하여 가져온다.
        (.env는 .gitignore에 등록되어 git 업로드 시 github에 업로드 되지 않아서 앱 키가 github에 노출되는 것을 방지한다.)
    */
    @Value("${kakao.local.key}")
    private String kakaoLocalKey;

    public Coordinates getCoordinate(String address) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        /*
        요청에 담아보낼 API key와 address 생성
            가져온 앱 키에 "KakaoAK "를 앞부분에 붙여서 Authorization에 넣어 보내야 정상적으로 요청이 이뤄진다.
            좌표로 변환할 address는 임의로 작성하였다.
            실사용에서는 이를 대체하여 해당 메서드의 input 매개변수로 String address 값을 불러와 사용하면 된다.
        */
        String apiKey = "KakaoAK " + kakaoLocalKey;
        // String address = "서울시 강남구 테헤란로 131";

        // 요청 헤더에 만들기, Authorization 헤더 설정하기
        // Spring에서 제공하는 HttpHeaders와 HttpEntity를 사용하면 HTTP 요청에 보낼 헤더를 손쉽게 설정할 수 있다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);


//        HTTP 요청에 포함할 쿼리 작성하기
//        Spring의 UriComponents를 사용하여 쿼리를 보낼 수 있다. fromHttpUrl에 필드에 생성한 uri 변수를 사용한다.
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(uri).queryParam("query", address).build();

//        RestTemplate
//        API 요청을 보내기 위해 Spring에서 제공하는 RestTemplate을 사용한다.
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);
        /*
        restTemplate로 API 요청하여 응답을 받는 메서드는 3가지가 존재한다.
            1. postForEntity()
            2. exchange()
            3. execute()
        이들 메서드의 차이는 아래 웹사이트에 잘 작성되어있으니 참고하기 바란다.
        https://www.baeldung.com/spring-resttemplate-exchange-postforentity-execute

        여기선 exchage() 함수를 사용하여 API 요청할 것이다. restTemplate의 exchange 함수에는 다음과 같은 매개변수가 필요하다.
            1. uri를 String 형태로
            2. Http 메서드
            3. HttpEntity
            4. 반환받을 변수 형식
        위 코드는 필요 매개변수를 순서대로 넣어준 것이다. Entity는 기존에 선언하여 Authorization에 api key가 있는 entity를 넣어줘야 한다.
        */

        /*
        API 응답으로부터 좌표 뽑아내기
            restTemplate의 exchage() 메서드의 반환 값의 형식은 가변적이다.
            여기서는 응답을 String.class로 지정하여 문자열로 받았는데,
            그 이유는 JSONObject를 사용하여 특정 키를 통해 좌표값을 뽑내기 위해서다.
        */
        // API Response로부터 Body 뽑아내기
        String body = response.getBody();
        JSONObject json = new JSONObject(body);

        // body에서 좌표 뽑아내기
        JSONArray documents = json.getJSONArray("documents");
        String x = documents.getJSONObject(0).getString("x");
        String y = documents.getJSONObject(0).getString("y");

        /*
        JSONObject는 getJSONArray(), getJSONObject() 그리고 getString()을 통해 JSON 값을 가져올 수 있다.
            getJSONArray()는 JSON 데이터에서 배열로 지정된 데이터를 뽑아낼 수 있고,
            getJSONObject()는 JSON 데이터에서 하위 JSON으로 선언된 데이터를 뽑아낼 수 있으며,
            getString()은 특정 키에 해당하는 값을 추출해낼 수 있다.
            세 메서드가 취급하는 데이터 양식이 상이하므로 서로 혼동하여 사용하지 않도록 주의한다.
        */

        return new Coordinates(x, y);
    }

}
