# 🌱 Spring Study Repository
> **Spring Framework** 를 단계적으로 학습하는 스터디 저장소입니다.  
> Maven, Lombok, AOP, Spring MVC까지 Spring의 핵심을 다룹니다.

<br>

## 🛠 Tech Stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge&logo=java&logoColor=white)

<br>

## 📂 폴더 구조

```
Spring/
├── 📁 mavenstudy/                    # Maven 빌드 도구
├── 📁 lombokstudy/                   # Lombok 어노테이션
├── 📁 aopstudy/                      # AOP(관점 지향 프로그래밍)
├── 📁 chartstudy/                    # Chart
└── 📁 shop1/                         # Spring shop1 프로젝트
```

<br>

## 📖 커리큘럼

| 단계 | 폴더 | 주제 | 핵심 개념 |
|:----:|------|------|-----------|
| 1 | `mavenstudy` | Maven | 빌드 도구, pom.xml, 의존성 관리 |
| 2 | `lombokstudy` | Lombok | @Getter/@Setter, @Builder, @AllArgsConstructor |
| 3 | `aopstudy` | AOP | Aspect, Pointcut, Advice, JoinPoint |
| 4 | `chartstudy` | Chart.js + JSP 데이터 시각화 | JSTL sql 태그로 MariaDB 조회 후 막대/선/파이 차트 렌더링 |
| 5 | `shop1` | Spring 쇼핑몰 풀스택 프로젝트 | Spring MVC, MyBatis, AOP, WebSocket, Ajax, 인터셉터, 사이트메시 |

<br>

## 🛒 shop1 — Spring 쇼핑몰 프로젝트 상세

### 📁 패키지 구조

```
shop1/src/main/
├── 📁 java/
│   ├── 📁 aop/                  # AOP (관점 지향 프로그래밍)
│   │   ├── AdminLoginAspect     # 관리자 로그인 체크
│   │   ├── CartAspect           # 장바구니 관련 AOP
│   │   └── LoginAspect          # 로그인 체크
│   ├── 📁 config/               # 설정 클래스
│   │   ├── DBConfig             # DB 연결 설정
│   │   ├── MvcConfig            # Spring MVC 설정
│   │   └── WebSocketConfig      # WebSocket 설정
│   ├── 📁 controller/           # 컨트롤러
│   │   ├── AdminController      # 관리자 기능
│   │   ├── AjaxController       # Ajax 요청 처리
│   │   ├── BoardController      # 게시판
│   │   ├── CartController       # 장바구니
│   │   ├── ChatController       # 채팅
│   │   ├── ItemController       # 상품
│   │   └── UserController       # 회원
│   ├── 📁 dao/                  # 데이터 접근 계층
│   │   ├── 📁 mapper/           # MyBatis 매퍼 인터페이스
│   │   │   ├── BoardMapper
│   │   │   ├── CommentMapper
│   │   │   ├── ItemMapper
│   │   │   ├── OrderItemMapper
│   │   │   ├── OrderMapper
│   │   │   └── UserMapper
│   │   └── (BoardDao, CommentDao, ItemDao, OrderDao, OrderItemDao, UserDao)
│   ├── 📁 dto/                  # 데이터 전송 객체
│   │   └── (Board, Cart, Comment, Item, ItemSet, Mail, Order, OrderItem, User, UserPassword)
│   ├── 📁 exception/            # 커스텀 예외
│   │   ├── CartException
│   │   ├── LoginException
│   │   └── ShopException
│   ├── 📁 handler/              # 핸들러
│   │   └── EchoHandler          # WebSocket 핸들러
│   ├── 📁 intercepter/          # 인터셉터
│   │   └── BoardIntercepter
│   ├── 📁 service/              # 서비스 레이어
│   │   ├── BoardService
│   │   ├── ItemService
│   │   ├── ShopService
│   │   └── UserService
│   ├── 📁 sitemesh/             # 사이트메시 (레이아웃 데코레이터)
│   │   └── SitemeshFilter
│   └── 📁 util/
│       └── ShopUtil
├── 📁 resources/
│   ├── logback.xml              # 로깅 설정
│   ├── mail.properties          # 메일 설정
│   ├── messages.properties      # 메시지 국제화
│   ├── mybatis-config.xml       # MyBatis 설정   
│   └── shop.sql                 # DB 스크립트
└── 📁 webapp/
    ├── 📁 WEB-INF/view/
    │   ├── 📁 admin/            # 관리자 페이지 (목록, 메일)
    │   ├── 📁 board/            # 게시판 (목록/상세/작성/수정/삭제/댓글)
    │   ├── 📁 cart/             # 장바구니 (카트/결제/주문내역)
    │   ├── 📁 chat/             # 채팅 (채팅방/챗봇/네이버검색)
    │   ├── 📁 item/             # 상품 (목록/상세/등록/수정/삭제)
    │   └── 📁 user/             # 회원 (로그인/회원가입/마이페이지/비밀번호/ID찾기)
    └── index.jsp
```

### ⚙️ 구현 기능

| 기능 | 설명 |
|------|------|
| 👤 회원 | 회원가입, 로그인/로그아웃, 마이페이지, 비밀번호 변경, ID/PW 찾기 |
| 📋 게시판 | 목록, 작성, 상세, 수정, 삭제, 댓글 |
| 🛍️ 상품 | 상품 목록/상세/등록/수정/삭제 |
| 🛒 장바구니 | 장바구니 담기, 결제, 주문내역 조회 |
| 💬 채팅 | WebSocket 실시간 채팅, 챗봇, 네이버 검색 연동 |
| 🔐 AOP | 로그인/관리자 권한 체크, 장바구니 AOP |
| 📧 메일 | 메일 발송 기능 |
| 🔍 검색 | 통합 검색 기능 |