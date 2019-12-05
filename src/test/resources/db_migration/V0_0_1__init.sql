CREATE SCHEMA IF NOT EXISTS "issue_9474";

CREATE TABLE IF NOT EXISTS "issue_9474"."book" (
  "id"            BIGSERIAL             NOT NULL
    CONSTRAINT "book_pk" PRIMARY KEY,

  "name"          VARCHAR(255) UNIQUE,
  "last_name"     VARCHAR(255),
  "isbn"          VARCHAR(255) UNIQUE,
  "pages"         INTEGER,
  "hot"           BOOL                  NOT NULL,
  "language"      VARCHAR(255),
  "publish_date"  DATE                  NOT NULL,
  "filter"        JSONB,
  "filters"       JSONB,
  "tags"          VARCHAR(255)[],
  "extra"         JSONB,
  "deleted"       BOOL    DEFAULT FALSE NOT NULL
);

INSERT INTO "issue_9474"."book"("name", "isbn", "pages", "hot", "language", "publish_date")
VALUES ('1984', 'asdf', 280, TRUE, 'English', '1949-01-01'::TIMESTAMP),
       ('Animal Farm', 'qwer', 320, FALSE, 'English', '1945-01-01'::TIMESTAMP);


UPDATE "issue_9474"."book"
SET "extra"   = '{
      "str": "string",
      "int": 2,
      "bool": true,
      "array": [1, 2, 3]
    }',
--     "filter"  = '{
--       "filter": "string",
--       "operator": "eq",
--       "value": "asdf"
--     }',
--     "filters" = '[
--       {
--         "filter": "string",
--         "operator": "eq",
--         "value": "asdf"
--       }
--     ]',
    "tags"    = '{tag1, tag2, tag3}'
WHERE "name" = '1984';