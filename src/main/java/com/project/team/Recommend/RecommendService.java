package com.project.team.Recommend;

import com.project.team.Restaurant.Restaurant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
public class RecommendService {

    public List<Recommend> byRain(String value) {
        List<Recommend> recommendList = new ArrayList<>();
        if (value.equals("1")) {
            recommendList.add(createRecommend("전", "비오는 날에는 전에 막걸리 한 잔!", "/image/food/파전.jpg"));
            recommendList.add(createRecommend("수제비", "비가 와서 서늘해진 날씨에 따뜻한 국물로 체온을 유지하세요.", "/image/food/수제비.jpg"));
            recommendList.add(createRecommend("라면", "눅눅해진 날씨에는 쾌적한 집에서 라면 한 그릇도 괜찮아요.", "/image/food/라면.jpg"));
        } else {
            recommendList.add(createRecommend("우동", "춥고 눈이 올때는 뜨거운 우동을 먹으며 몸을 녹여보세요.", "/image/food/우동.png"));
            recommendList.add(createRecommend("양식", "눈이 오는 특별한 날에는 연인과 함께 레스토랑에서 데이트는 어떠신가요?", "/image/food/양식.jpg"));
            recommendList.add(createRecommend("샤브샤브", "따뜻한 육수와 겨울에 부족할 수 있는 비타민을 야채로 섭취해보세요.", "/image/food/샤브샤브.jpg"));
        }
        return recommendList;
    }

    public List<Recommend> byTemp(String value) {
        List<Recommend> recommendList = new ArrayList<>();
        if (Integer.parseInt(value) < 10) {
            recommendList.add(createRecommend("국밥", "추운 날씨에는 역시 속까지 따뜻하고 든든해지는 국밥이죠.", "/image/food/국밥.jpg"));
            recommendList.add(createRecommend("해산물", "겨울 제철인 해산물이 많다고 해요. 제철인 해산물을 즐겨보세요.", "/image/food/해산물.jpg"));
            recommendList.add(createRecommend("고기", "겨울에는 불 앞에서 구워먹을 수 있는 고기도 좋아요.", "/image/food/삼겹살.jpg"));
        } else if ((10 <= Integer.parseInt(value)) && Integer.parseInt(value) < 23) {
            recommendList.add(createRecommend("치킨", "좋은 날씨에 야외에서 간단한 치맥은 어떠신가요?", "/image/food/치킨.jpg"));
            recommendList.add(createRecommend("브런치", "마찬가지로 이런 날씨에는 야외에서 즐길 수 있는 브런치도 인기가 많아요.", "/image/food/브런치.png"));
            recommendList.add(createRecommend("한정식","가족들과 함께 나들이 후 즐길 수 있는 한정식도 좋아요.", "/image/food/한정식.jpg"));
        } else {
            recommendList.add(createRecommend("삼계탕", "더위에 지친 몸을 회복할 삼계탕을 추천해드릴게요.", "/image/food/삼계탕.jpg"));
            recommendList.add(createRecommend("냉면", "더운 날씨에는 역시 시원한 냉면이 최고죠.", "/image/food/냉면.jpg"));
            recommendList.add(createRecommend("아이스크림","더위를 한번에 날릴 수 있는 아이스크림은 어떠신가요?", "/image/food/아이스크림.jpg"));
        }
        return recommendList;
    }

    private Recommend createRecommend(String menu, String explanation, String image) {
        Recommend recommend = new Recommend();
        recommend.setMenu(menu);
        recommend.setExplanation(explanation);
        recommend.setImage(image);
        return recommend;
    }

}
