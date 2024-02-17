# product

이커머스 상품 프로젝트

## Quick Start

1. 도커 실행

```
docker-compose -f ./docker/docker-compose.yml up -d 
```

2. 빌드

``` 
./gradlew build
```

3. flyway 실행

```
./gradlew flywayClean flywayBaseline flywayMigrate
```
