This repository is used to reproduce the jooq issue [#9474](https://github.com/jOOQ/jOOQ/issues/9474)

It builds on `jooq v3.11.11` and the tests should be by default.

### Run the test
* **Postgres** db is required.
* Update `application.yml` and `gradle.properties` for your local db connection.
```
./gradlew doMigration
./gradlew generateIssue9474JooqSchemaSource
./gradlew test
```

### To switch to `jooq v3.12.1`
1. build.gradle
    ```
    buildscript {
    //	ext.jooqVersion = '3.11.11'
        ext.jooqVersion = '3.12.1'
    }
    ```
    ```
                    forcedTypes {
                        forcedType {
                            userType = 'com.fasterxml.jackson.databind.JsonNode'
    //						binding = 'org.jooq4.issue.JsonbBinding11'
                            binding = 'org.jooq4.issue.JsonbBinding12'
                            types = 'jsonb'
                        }
                    }
    ```
2. Uncomment [JsonBinding12.java](src/main/java/org/jooq4/issue/JsonbBinding12.java)
3. `./gradlew clean test`

### Walk around
* This issue could be walked around by introducing
`RecordUnmapper` provided by [SimpleFlatMapper](https://github.com/arnaudroger/SimpleFlatMapper)
