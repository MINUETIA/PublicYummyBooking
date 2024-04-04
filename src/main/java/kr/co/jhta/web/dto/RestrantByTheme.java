package kr.co.jhta.web.dto;

import lombok.Data;

@Data
public class RestrantByTheme {
    int restaurant_id;
    String theme_name;
    String theme_classify_code;
    int theme_id;
    int num;
}
