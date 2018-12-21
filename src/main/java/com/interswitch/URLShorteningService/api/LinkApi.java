package com.interswitch.URLShorteningService.api;

import com.interswitch.URLShorteningService.DAO.UrlDao;
import com.interswitch.URLShorteningService.services.ShortenLink;
import com.interswitch.URLShorteningService.api.model.SubmitRequest;
import com.interswitch.URLShorteningService.api.model.SubmitResponse;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import com.interswitch.URLShorteningService.api.model.Error;

@RestController
@RequestMapping("api")
public class LinkApi {


    private SubmitResponse getLinkResponse;
    //In memory cache HashMap for faster Access
    private Map<String,SubmitResponse> dictionary = new HashMap<String,SubmitResponse>();

    //Submit a Link to shorten. Generates a Link Id, collects an Object with only one Property {Url}
    @PostMapping("/submit")
    @ResponseBody
    public SubmitResponse submit(@RequestBody SubmitRequest submitRequest) throws Exception {
        AtomicBoolean isFound = new AtomicBoolean(false);

        for(SubmitResponse submitResponse : dictionary.values()){
            if(submitResponse.getOldUrl() == submitRequest.getUrl()){
                isFound.set(true);
                getLinkResponse = new SubmitResponse(
                        "",submitRequest.getUrl(),null,"200","URL Already Exists",null
                );
            }
        }

        while (!isFound.get()){
            if(UrlDao.getOldurl(submitRequest.getUrl())){
                isFound.set(true);
                getLinkResponse = new SubmitResponse(
                        "",submitRequest.getUrl(),null,"200","URL Already Exists",null
                );
            }
            break;
        }

        while(isFound.get() == false){
            String LinkId = ShortenLink.generateShortUrl(7);
            getLinkResponse = new SubmitResponse(LinkId,submitRequest.getUrl(),new Date(),"200","Link was successfully created on "+new Date()+"",null);

            try{
                if(UrlDao.submitLink(getLinkResponse)){
                    dictionary.put(LinkId,getLinkResponse);
                }
            }
            catch(Exception ex){
                getLinkResponse = new SubmitResponse(null,submitRequest.getUrl(),null,"400","Error Shortening Url",new ArrayList<Error>() {  } );
            }

            break;
        }

        return getLinkResponse;
    }

    //Find a link by LinkId
    @GetMapping("/getlink/{KeyId}")
    @ResponseBody
    public SubmitResponse getLink(@PathVariable("KeyId") String Id) throws Exception {

        if(dictionary.containsKey(Id)){
            return dictionary.get(Id);
        }
        else if(!dictionary.containsKey(Id)) {
            return UrlDao.getLink(Id);
        }
        else{
            return getLinkResponse = new SubmitResponse("0", null ,null,"404","Not Found",null);
        }

    }

    //Return all Shortened Links
    @GetMapping("/getall")
    public  Map<String,SubmitResponse> getUrls () throws Exception {

        if (dictionary.values().size() > 0){
            return dictionary;
        }
        else {
            return UrlDao.getAll();
        }
    }


}
