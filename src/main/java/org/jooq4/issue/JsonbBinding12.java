package org.jooq4.issue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

//public class JsonbBinding12 implements Binding<JSONB, JsonNode> {
//
//    private ObjectMapper mapper;
//
//    public JsonbBinding12() {
//        this.mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    }
//
//    @Override
//    public Converter<JSONB, JsonNode> converter() {
//        return new Converter<>() {
//
//            private static final long serialVersionUID = -558287364744020893L;
//
//            @Override
//            public JsonNode from(JSONB t) {
//                try {
//                    if (t == null || t.toString() == null) return null;
//                    return mapper.readTree(t.toString());
//                } catch (IOException e) {
//                    throw new IllegalArgumentException(e);
//                }
//            }
//
//            @Override
//            public JSONB to(JsonNode u) {
//                try {
//                    return u == null ? null : JSONB.valueOf(mapper.writeValueAsString(u));
//                } catch (JsonProcessingException e) {
//                    throw new IllegalArgumentException(e);
//                }
//            }
//
//            @Override
//            public Class<JSONB> fromType() {
//                return JSONB.class;
//            }
//
//            @Override
//            public Class<JsonNode> toType() {
//                return JsonNode.class;
//            }
//        };
//    }
//
//    // Rending a bind variable for the binding context's value and casting it to the json type
//    @Override
//    public void sql(BindingSQLContext<JsonNode> ctx) {
//        ctx.render().visit(DSL.val(ctx.convert(converter()).value())).sql("::jsonb");
//    }
//
//    // Registering VARCHAR types for JDBC CallableStatement OUT parameters
//    @Override
//    public void register(final BindingRegisterContext<JsonNode> ctx) throws SQLException {
//        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
//    }
//
//    // Converting the JsonElement to a String value and setting that on a JDBC PreparedStatement
//    @Override
//    public void set(BindingSetStatementContext<JsonNode> ctx) throws SQLException {
//        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
//    }
//
//    // Getting a String value from a JDBC ResultSet and converting that to a JsonElement
//    @Override
//    public void get(BindingGetResultSetContext<JsonNode> ctx) throws SQLException {
//        ctx.convert(converter()).value(JSONB.valueOf(ctx.resultSet().getString(ctx.index())));
//    }
//
//    // Getting a String value from a JDBC CallableStatement and converting that to a JsonElement
//    @Override
//    public void get(BindingGetStatementContext<JsonNode> ctx) throws SQLException {
//        ctx.convert(converter()).value(JSONB.valueOf(ctx.statement().getString(ctx.index())));
//    }
//
//    // Setting a value on a JDBC SQLOutput (useful for Oracle OBJECT types)
//    @Override
//    public void set(BindingSetSQLOutputContext<JsonNode> ctx) throws SQLException {
//        throw new SQLFeatureNotSupportedException();
//    }
//
//    // Getting a value from a JDBC SQLInput (useful for Oracle OBJECT types)
//    @Override
//    public void get(BindingGetSQLInputContext<JsonNode> ctx) throws SQLException {
//        throw new SQLFeatureNotSupportedException();
//    }
//}
