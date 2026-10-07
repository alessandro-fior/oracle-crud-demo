mvn "-Dmaven.repo.local=target/m2-repository" spring-boot:run

# test

#mvn '-Dspring.profiles.active=test' test '-Dtest=BulkInsertTest,CacheEvictionTest,ComplexQueryTest'
#mvn '-Dspring.profiles.active=test' test '-Dtest=BulkInsertTest,CacheEvictionTest,ComplexQueryTest'
#mvn '-Dspring.profiles.active=test' test '-Dtest=BulkInsertTest'