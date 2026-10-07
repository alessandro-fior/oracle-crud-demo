mvn "-Dmaven.repo.local=target/m2-repository" spring-boot:run

mvn '-Dspring.profiles.active=test' test '-Dtest=BulkInsertTest,CacheEvictionTest,ComplexQueryTest'
mvn '-Dspring.profiles.active=test' test '-Dtest=BulkInsertTest,CacheEvictionTest,ComplexQueryTest'
mvn '-Dspring.profiles.active=test' test '-Dtest=BulkInsertTest'