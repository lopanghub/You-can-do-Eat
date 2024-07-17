dev2.1 

1. .env 파일을 배포할 예정입니다.(필요시 application.properties)도 프로젝트 파일에 넣어주세요

프로젝트 시작시 .env의 값을 읽도록 메인 메서드에 관련 함수를 추가 했습니다.

관련해서 config 파일 두개가 추가되었습니다.(S3Config, DotenvConfig)

1. build.gradle

황희님이 안쓰시는 페이지 관련 의존성 삭제 

추가된 의존성 

```
implementation 'software.amazon.awssdk:s3:2.20.0' // 최신 버전으로 업데이트
    implementation 'com.google.code.gson:gson:2.8.8' // Gson 라이브러리 추가
    
    implementation 'io.github.cdimascio:java-dotenv:5.2.2' //환경변수 읽어오는 라이브러리
}
```

그 외에도 라이브러리 충돌때문에 버전을 몇몇 라이브러리들은 버전이 명시되도록 변경

1. application.properties
아마존 관련 프로퍼티가 추가되었습니다. 

아마존에 의해 푸시가 막힐수도 있어서 .gitignore에 application.properties를 추가하겠습니다 
aws 쓰는 사람이 저 뿐이라 이렇게 하면 관련 깃 에러는 겪지 않을거 같습니다.

1. S3Service S3Controller - 파일을 AWS S3(아마존 저장소)에 올리고 다운받는 용도입니다.
2. AWSService AWSContorller - 다운받은 json을 파일을 파싱해서 테이블에 데이터로 집어 넣는 용도입니다.
3. RecipeMapper, xml파일에 필요한 메서드들 추가했습니다(주석 처리됨)
4. boardTable.sql 쿼리문에 진짜 최소한의 교통정리를 했습니다. 기존 테이블들 드랍하고 다시 넣으시면 됩니다.
5. ps. 황희님 apoint 에러 수정 안된버전이니까(토요일 19시 버전) 수정해주시고 AWSController에 주석으로 에러 막아놨는데 풀어주세요
