# 🗺️ About OtakuMap

![image](https://github.com/user-attachments/assets/16e092a0-3b0e-4bb3-8cb4-9ed360662208)

애니메이션을 좋아하는 여러분, 여행을 떠나고 싶은데 어디부터 가야 할지 고민되시나요? ✈️

오타쿠맵은 **애니메이션 팬들을 위한 맞춤형 여행 플랜 서비스**입니다!

콜라보 카페, 팝업스토어, 전시회 등 다양한 이벤트 정보를 한곳에서 확인하고, 여러분만의 성지순례 루트를 쉽게 계획할 수 있어요.

🏮 **애니메이션 관련 이벤트와 핫플을 한눈에!**

좋아하는 애니메이션과 관련된 팝업스토어, 이벤트, 유명 관광지를 한 번에 모아볼 수 있어요.

🔍 **장르별 애니 & 이벤트 검색 기능**

원하는 애니메이션이나 특정 장르의 이벤트를 빠르게 검색할 수 있어요. 좋아하는 작품과 관련된 여행지를 손쉽게 찾아보세요!

📍 **나만의 오타쿠 여행 루트 만들기**

마음에 드는 장소와 이벤트를 저장하고 즐겨찾기할 수 있어요. 지도를 활용해 핀을 찍고, 원하는 순서대로 자유롭게 여행을 계획해보세요!

📝 **후기 공유 및 커스터마이징**

이벤트나 성지순례 장소에 대한 후기를 작성하고 공유할 수 있어요. 다른 팬들의 생생한 경험을 참고하며 더욱 완벽한 여행을 준비해보세요!

# ✨기능 소개
## 1) 이벤트 정보 
- 진행 중인 인기 이벤트
 ![image](https://github.com/user-attachments/assets/8baccc62-439c-4208-b790-cfe5701842a3)
- 이벤트 상세 조회 (이름, 일자, 위치, 판매제품, 후기, 공식 사이트로 이동 가능)
  ![image](https://github.com/user-attachments/assets/54b39392-b23d-41c1-992a-10022d39ef4d)
  ![image](https://github.com/user-attachments/assets/2cb9108d-a5f1-4c48-8fe7-9ef8c02a4e78)
- 한 줄 리뷰 및 후기 확인
  ![image](https://github.com/user-attachments/assets/dd3730f1-6e28-4fc6-9c2c-502cd38c4732)

## 2) 후기 게시글 공유
- TOP 7 여행 후기
  ![image](https://github.com/user-attachments/assets/eff30798-603b-484d-8557-c64ced7cb41e)
- 후기 개별 조회
  ![image](https://github.com/user-attachments/assets/da009289-c497-4741-80a2-2830229fdbe1)
- 유료 후기일 경우 포인트로 결제
  ![image](https://github.com/user-attachments/assets/1fb037d1-e035-4c36-b571-b1dc6be3884c)
  ![image](https://github.com/user-attachments/assets/000ac862-4054-4264-80a6-5ab70a080a0f)

## 3) 원하는 루트/장소/이벤트 저장 및 즐겨찾기
  ![image](https://github.com/user-attachments/assets/39dcfecf-7234-45db-b0f0-fbcc54bdd2e0)
  ![image](https://github.com/user-attachments/assets/fa0ba2d1-6bbd-4529-a982-3dadc47855bf)

## 4) 장르별 애니 및 이벤트 검색
  ![image](https://github.com/user-attachments/assets/38ba14f2-8541-4d60-be80-9ea93a29737e)

## 5) 지도 기반 서비스
  - 루트 순서 및 제목 변경 후 커스텀 저장 가능
  - 관련된 장소 정보 조회 가능
    ![image](https://github.com/user-attachments/assets/5febfce8-3a75-4f48-bf4a-041aa63102af)

# 🚀 ERD 설계 
![image](https://github.com/user-attachments/assets/f06a596b-ce2a-4677-9f43-a56952e14fde)

# 📂 구조
  - 도메인별로 테이블을 나누었고 도메인마다 필요한 `controller`, `service`, `repository`, `enums`, `converter`, `dto` 적절히 사용
```
Otaku_SB/src
├── main
│   └── java
│       └── com.otakumap
│           ├── domain
│           │   ├── animation
│           │   ├── auth
│           │   ├── event
│           │   │   ├── event_animation.repository
│           │   │   ├── event_like
│           │   │   ├── event_location
│           │   │   ├── event_review
│           │   │   ├── event_review_place.repository
│           │   │   ├── event_short_review
│           │   │   ├── event_short_review_reaction
│           │   ├── hash_tag
│           │   ├── image
│           │   ├── map
│           │   ├── mapping
│           │   ├── notification
│           │   ├── payment
│           │   ├── place
│           │   │   ├── place_animation.repository
│           │   │   ├── place_like
│           │   │   ├── place_review
│           │   │   ├── place_review_place.repository
│           │   │   ├── place_short_review
│           │   ├── point
│           │   ├── reviews
│           │   ├── route
│           │   │   ├── route_item
│           │   │   ├── route_like
│           │   ├── search
│           │   ├── transaction
│           │   ├── user
│           │   ├── user_reaction
│           ├── global
│           │   ├── apiPayload
│           │   ├── common
│           │   ├── config
│           │   ├── util
│           │   ├── validation
│           ├── OtakumapApplication
├── resources
│   └── application.yml
```

# 🧑‍💻 사용한 백엔드 개발 환경
![image](https://github.com/user-attachments/assets/4ee2cbc6-0aa5-4cbe-9c66-00ef4d6aefba)
