package com.interswitch.URLShorteningService.DAO;

import com.interswitch.URLShorteningService.api.model.SubmitResponse;
import com.interswitch.URLShorteningService.config.DataSourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UrlDao {


    private static JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceConfig.getDataSource());

    @Autowired
    private static NamedParameterJdbcTemplate parameterJdbcTemplate;

    public static boolean submitLink(SubmitResponse submitResponse) throws SQLException{
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        SqlParameterSource params = new MapSqlParameterSource();
        ((MapSqlParameterSource) params).addValue("LinkId",submitResponse.getLinkId());
        ((MapSqlParameterSource) params).addValue("OldUrl",submitResponse.getOldUrl());
        ((MapSqlParameterSource) params).addValue("Domain",SubmitResponse.getDomain());
        ((MapSqlParameterSource) params).addValue("DomainUrl",submitResponse.getDomainUrl());
        ((MapSqlParameterSource) params).addValue("DateCreated",submitResponse.getDateCreated());


        SimpleJdbcCall insertProcedure = new SimpleJdbcCall(jdbcTemplate).withSchemaName("dbo").withProcedureName("InsertLink");
        insertProcedure.execute(params);
        return true;
    }

    public static boolean getOldurl(String Url){

        jdbcTemplate.setResultsMapCaseInsensitive(true);
        SqlParameterSource params = new MapSqlParameterSource();
        ((MapSqlParameterSource) params).addValue("OldUrl",Url);
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("dbo").withProcedureName("getUrl");
        Map<String,Object> map = simpleJdbcCall.execute(params);

        if(map.values().size() > 1){
            return true;
        }

        return false;
    }

    public static SubmitResponse getLink(String Id) throws Exception{
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        SqlParameterSource params = new MapSqlParameterSource("LinkId",Id);
        SimpleJdbcCall getUrlProc = new SimpleJdbcCall(jdbcTemplate).withSchemaName("dbo").withProcedureName("getLink");
        Map<String,Object> map = getUrlProc.execute(params);

        SubmitResponse submitResponse = null;

        if (map.values().size() > 0){
            submitResponse = new SubmitResponse((String) map.get("LinkId"), (String) map.get("OldUrl"), (Date) map.get("DateCreated"), "200", "URL Already Exists",null);
        }

        return submitResponse;
    }

    public static Map<String,SubmitResponse> getAll() throws Exception{

        jdbcTemplate.setResultsMapCaseInsensitive(true);
        Collection<SubmitResponse> cs = jdbcTemplate.query("select * from Link_tbl",new RowMapper(){
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                SubmitResponse values = new SubmitResponse(rs.getString("LinkId"),rs.getString("OldUrl"),rs.getDate("DateCreated"),"200","Link was successfully created on "+rs.getString("DateCreated")+"",null);
                return values;
            }
        });

        Map<String,SubmitResponse> responseMap = new HashMap<String,SubmitResponse>();
        for (SubmitResponse sr : cs){
            responseMap.put(sr.getLinkId(),sr);
        }
        return responseMap;
    }

}
