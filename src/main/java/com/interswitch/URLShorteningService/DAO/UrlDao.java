package com.interswitch.URLShorteningService.DAO;

import com.interswitch.URLShorteningService.api.model.SubmitResponse;
import com.interswitch.URLShorteningService.config.DataSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UrlDao {


    private static JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceConfig.getDataSource());

    @Autowired
    private static NamedParameterJdbcTemplate parameterJdbcTemplate;

    public static boolean submitLink(SubmitResponse submitResponse) throws Exception{
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        SqlParameterSource params = new MapSqlParameterSource();
        ((MapSqlParameterSource) params).addValue("LinkId",submitResponse.getLinkId());
        ((MapSqlParameterSource) params).addValue("OldUrl",submitResponse.getOldUrl());
        ((MapSqlParameterSource) params).addValue("Domain",SubmitResponse.getDomain());
        ((MapSqlParameterSource) params).addValue("DomainUrl",submitResponse.getDomainUrl());
        ((MapSqlParameterSource) params).addValue("ResponseCode",submitResponse.getResponseCode());
        ((MapSqlParameterSource) params).addValue("ResponseMessage",submitResponse.getResponseMessage());

        SimpleJdbcCall insertProcedure = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("InsertLink");

        insertProcedure.execute(params);

        return true;
    }

    public static SubmitResponse getLink(String Id) throws Exception{
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        SimpleJdbcCall getLinkProc = new SimpleJdbcCall(jdbcTemplate).withSchemaName("dbo").withProcedureName("getLink");
        SqlParameterSource params = new MapSqlParameterSource("LinkId",Id);
        Map<String, Object> map = getLinkProc.execute(params);

        return (SubmitResponse) map.values();
    }

    public static Map<String,SubmitResponse> getall() throws Exception{

        jdbcTemplate.setResultsMapCaseInsensitive(true);
        SimpleJdbcCall getallProcedure = new SimpleJdbcCall(jdbcTemplate);

        Map<String,Object> map = getallProcedure.execute();
        Map<String,SubmitResponse> responseMap = null;
        (SubmitResponse) map.values();
        
        return map;
    }






}
