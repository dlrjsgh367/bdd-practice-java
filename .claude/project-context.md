# Demo Project Context

## 프로젝트 개요
- **프로젝트명**: Demo
- **타입**: Spring Boot REST API
- **주요 기술 스택**: Spring Boot 3.4.0, JPA, QueryDSL, SpringDoc OpenAPI, MapStruct
- **데이터베이스**: MySQL (개발), H2 (테스트)
- **패키지 구조**: DDD (Domain-Driven Design) 기반

## 기술 스택 상세

### Core
- Java 17
- Spring Boot 3.4.0
- Spring Data JPA
- Spring Validation

### Database
- MySQL Connector (Runtime)
- H2 Database (테스트용)

### Query & Mapping
- QueryDSL 5.1.0 (jakarta)
- MapStruct 1.5.5.Final
- Lombok

### API Documentation
- SpringDoc OpenAPI 2.6.0
  - Swagger UI: `/swagger-ui.html`
  - ReDoc: `/redoc.html`
  - OpenAPI JSON: `/api-docs`

## 프로젝트 구조

```
src/main/java/com/example/demo/
├── DemoApplication.java
├── config/
│   ├── QuerydslConfig.java
│   └── OpenApiConfig.java
└── domain/
    └── user/
        ├── model/
        │   └── User.java                    # 도메인 모델
        ├── infra/
        │   ├── data/
        │   │   ├── entity/
        │   │   │   └── UserEntity.java      # JPA 엔티티
        │   │   └── repository/
        │   │       ├── UserJpaRepository.java
        │   │       ├── UserCustomRepository.java
        │   │       └── UserCustomRepositoryImpl.java  # QueryDSL 구현
        │   ├── UserInfra.java               # 인프라 인터페이스
        │   ├── UserInfraImpl.java           # 인프라 구현
        │   └── UserInfraMapper.java         # MapStruct 매퍼
        ├── usecase/
        │   ├── UserUseCase.java             # 유즈케이스 인터페이스
        │   └── UserUseCaseImpl.java         # 유즈케이스 구현
        └── api/
            ├── UserEndpoint.java            # REST Controller
            └── dto/
                ├── req/
                │   ├── UserCreateReq.java
                │   └── UserUpdateReq.java
                └── res/
                    └── UserRes.java
```

## 데이터베이스 설정

### MySQL 연결 정보
- URL: `jdbc:mysql://localhost:3306/bdd_practice`
- Username: `root`
- Password: `root1234`
- Hibernate DDL: `create` (자동 테이블 생성)

## 해결했던 주요 이슈

### 1. Spring Boot 4.0.0 호환성 문제
**증상**: `BeanCreationException` - QueryDSL Predicate Operation Customizer 빈 생성 실패

**원인**:
- Spring Boot 4.0.0은 아직 안정화되지 않음
- SpringDoc 2.3.0/2.8.4와 호환성 문제
- `ClassNotFoundException: org.springframework.data.util.TypeInformation` 발생

**해결**: Spring Boot 버전 다운그레이드
```gradle
// Before
id 'org.springframework.boot' version '4.0.0'

// After
id 'org.springframework.boot' version '3.4.0'
```

### 2. SpringDoc 버전 조정
**최종 선택**: SpringDoc 2.6.0
- Spring Boot 3.x와 안정적으로 호환
- QueryDSL 5.1.0 지원

### 3. ReDoc 404 에러
**증상**: `/redoc` 경로 접속 시 404 에러

**해결**: 수동으로 ReDoc HTML 파일 생성
- 위치: `src/main/resources/static/redoc.html`
- CDN을 통한 ReDoc Standalone 사용
- OpenAPI 스펙 경로: `/api-docs`

## API 엔드포인트

### User API (`/api/v1/users`)

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/users` | 사용자 생성 |
| PUT | `/api/v1/users/{id}` | 사용자 수정 |
| GET | `/api/v1/users/{id}` | ID로 사용자 조회 |
| GET | `/api/v1/users/email/{email}` | 이메일로 사용자 조회 |
| GET | `/api/v1/users` | 전체 사용자 목록 |
| GET | `/api/v1/users/active` | 활성 사용자 목록 |
| GET | `/api/v1/users/search?name={name}` | 이름으로 검색 |
| DELETE | `/api/v1/users/{id}` | 사용자 삭제 |
| PATCH | `/api/v1/users/{id}/activate` | 사용자 활성화 |
| PATCH | `/api/v1/users/{id}/deactivate` | 사용자 비활성화 |

## 빌드 & 실행

### Gradle 빌드
```bash
./gradlew clean build
```

### 애플리케이션 실행
```bash
./gradlew bootRun
```

## API 문서 접속

애플리케이션 실행 후 브라우저에서:

1. **Swagger UI** (인터랙티브 테스트 가능)
   ```
   http://localhost:8080/swagger-ui.html
   ```

2. **ReDoc** (깔끔한 문서 UI)
   ```
   http://localhost:8080/redoc.html
   ```

3. **OpenAPI JSON** (원본 스펙)
   ```
   http://localhost:8080/api-docs
   ```

## 주요 패턴 및 규칙

### 1. DDD 레이어 구조
- **Domain Model**: 순수 비즈니스 로직
- **UseCase**: 애플리케이션 로직
- **Infrastructure**: 데이터베이스, 외부 시스템 연동
- **API**: REST 엔드포인트

### 2. DTO 변환
- **Request DTO** → Domain Model (UseCase에서)
- **Domain Model** → Response DTO (정적 팩토리 메서드 `from()`)
- **Entity** ↔ **Domain Model** (MapStruct 사용)

### 3. Repository 패턴
- **JpaRepository**: 기본 CRUD
- **CustomRepository**: QueryDSL 기반 복잡한 쿼리
- **Infra 레이어**: Repository를 감싸서 도메인 모델 반환

## 다음 작업 가능한 항목

1. 예외 처리 (`@RestControllerAdvice`)
2. 페이징 처리
3. 인증/인가 (Spring Security)
4. 테스트 코드 작성
5. 로깅 AOP
6. 다른 도메인 추가

## 참고사항

- QueryDSL Q클래스는 `build/generated/sources/annotationProcessor/java/main/`에 생성됨
- Lombok과 MapStruct 함께 사용 시 `lombok-mapstruct-binding` 필요
- `open-in-view: false` 설정으로 OSIV 비활성화 (명시적 트랜잭션 관리)
