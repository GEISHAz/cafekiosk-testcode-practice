# cafekiosk-testcode-practice
☕️ TestCode를 경험하기 위한 간단 카페 키오스크 프로젝트 : A project to experience writing TestCode using JUnit 5.

JPA와 Spring Boot를 활용하여 간단한 카페 키오스크 시스템을 구현하는 프로젝트입니다. CRUD 기능 중심으로 주문 목록 관리 및 총액 계산을 연습하며, 백엔드 개발과 Junit5를 활용한 테스트 코드 작성 능력을 향상시키는 것을 목표로 합니다.

## 🚀 주요 기능
- 주문 목록에 음료 추가/삭제
- 주문 목록 전체 지우기
- 주문 목록 전체 금액 계산
- 주문 생성하기

## 🛠 기술 스택

| 기술            | 목적                              |
|-----------------|-----------------------------------|
| **Java**        | 주요 프로그래밍 언어              |
| **Spring Boot** | 백엔드 프레임워크                |
| **JPA**         | 데이터베이스 상호작용            |
| **H2**          | 인메모리 데이터베이스            |
| **Lombok**      | 코드 간소화를 위한 어노테이션 제공 |
| **Spring Web**  | REST API 개발                    |

## 📂 프로젝트 구조
```
SampleCafeKiosk/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── sample.cafekiosk/
│   │   │       ├── controller/     # API 컨트롤러
│   │   │       ├── service/        # 비즈니스 로직
│   │   │       ├── entity/         # JPA 엔티티
│   │   │       ├── dto/            # 데이터 전송 객체
│   │   │       └── repository/     # 데이터 액세스 레이어
│   │   └── resources/
│   │       ├── application.yml     # 애플리케이션 설정 파일
│   │       └── data.sql            # H2 샘플 데이터
│   └── test/                       # 단위 테스트
└── build.gradle                    # Gradle 의존성 관리

