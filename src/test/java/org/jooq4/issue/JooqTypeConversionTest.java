package org.jooq4.issue;

import com.fasterxml.jackson.databind.ObjectMapper;
import jooq.Tables;
import model.Book;
import model.FilterCondition;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Rollback
@Transactional
public class JooqTypeConversionTest {

    @Autowired
    private DSLContext dslContext;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testTypeConversion() {
        //     Java         <->    Jooq     <->    SQL              ::
        // 1. java.time     <-> java.time   <-> timestamp/date      ::  Book.publishDate
        // 2. JsonNode      <-> jsonNode    <-> jsonb               ::  Book.extra
        // 3. {Model}       <-> JsonNode    <-> jsonb               ::  Book.filter
        // 4. List<String>  <-> String[]    <-> varchar[]           ::  Book.tags
        // 5. List<Model>   <-> JsonNode    <-> jsonb               ::  Book.filters
        Book book = dslContext.selectFrom(Tables.BOOK).where(Tables.BOOK.NAME.eq("1984"))
                .fetchOne().into(Book.class);
        // 1.
        assertThat(book.publishDate, equalTo(LocalDate.parse("1949-01-01")));
        // 2.
        assertThat(book.extra, notNullValue());
        assertThat(book.extra.get("str").textValue(), equalTo("string"));
        // 3. ModelMapper or sfm is needed to do the RecordMapper job.
//        assertThat(book.filter, notNullValue());
//        assertThat(book.filter.filter, equalTo("string"));
//        assertThat(book.filter.operator, equalTo("eq"));
//        assertThat(book.filter.value, equalTo("asdf"));
        // 4.
        assertThat(book.tags, hasSize(3));
        assertThat(book.tags, everyItem(notNullValue(String.class)));
        // 5. ModelMapper or sfm is needed to do the RecordMapper job.
//        assertThat(book.filters, notNullValue());
//        assertThat(book.filters, hasSize(1));
//        assertThat(book.filters.get(0).operator, equalTo("eq"));
//        assertThat(book.filters.get(0).value, equalTo("asdf"));
    }

    @Test
    public void testWriteTypeConversion() {
        //     Java         <->    Jooq     <->    SQL              ::
        // 1. java.time     <-> java.time   <-> timestamp/date      ::  Book.publishDate
        // 2. JsonNode      <-> jsonNode    <-> jsonb               ::  Book.extra
        // 3. {Model}       <-> JsonNode    <-> jsonb               ::  Book.filter
        // 4. List<String>  <-> String[]    <-> varchar[]           ::  Book.tags
        Book book = new Book();
        book.name = "test book";
        book.isbn = "test isbn";
        book.pages = 100;
        book.language = "test language";
        book.publishDate = LocalDate.of(2020, 2, 1);                    // 1.
        book.extra = objectMapper.valueToTree(Map.of("str", "qqqq", "int", 7));      // 2.
        book.filter = new FilterCondition();                                                    // 3.
        book.filter.filter = "string";
        book.filter.operator = "eq";
        book.filter.value = "qwer";

        book.tags = List.of("qwer", "asdf", "zxcv");                                            // 5.

        var bookRecord = dslContext.newRecord(Tables.BOOK);
        bookRecord.from(book);
        // 1.
        assertThat(bookRecord.getPublishDate(), equalTo(LocalDate.parse("2020-02-01")));
        // 2.
        assertThat(bookRecord.getExtra(), notNullValue());
        assertThat(bookRecord.getExtra().get("str").textValue(), equalTo("qqqq"));
        // 3.
        assertThat(bookRecord.getFilter(), notNullValue());
        assertThat(bookRecord.getFilter().get("filter").textValue(), equalTo("string"));
        assertThat(bookRecord.getFilter().get("operator").textValue(), equalTo("eq"));
        assertThat(bookRecord.getFilter().get("value").textValue(), equalTo("qwer"));
        // 4.
        assertThat(bookRecord.getTags(), arrayWithSize(3));
    }

}