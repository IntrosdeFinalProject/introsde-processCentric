package introsde.rest.processCentric;

import introsde.rest.models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import javax.ejb.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.glassfish.jersey.client.ClientConfig;
import org.json.*;
import org.apache.http.client.ClientProtocolException;



@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/processCentric")
public class ProcessCentric {

	
    @PUT
    @Path("/updateHP")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHP(LifeStatus ls) throws IOException {

        //Update measures of life status
        String ENDPOINT = "https://fathomless-journey-7209.herokuapp.com/introsde/storage/updateHP/1";
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        
        WebTarget service = client.target(ENDPOINT);

        Response res = null;
        String putResp = null;
        
        String updateHP ="{" + "\"measureName\":\""+ls.getMeasureName()+"\","
                        + "\"value\":\""+ls.getValue()+"\"}";
        
        res = service.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateHP));
        putResp = res.readEntity(String.class);
        
        if(res.getStatus() != 200 ){
            return Response.status(400).build();
        }
        
        // Comparing the updated life status measures with the goals
        String ENDPOINT2 = "https://limitless-chamber-1231.herokuapp.com/introsde/businessLogic/compare/"+ls.getMeasureName();
        DefaultHttpClient client1 = new DefaultHttpClient();
        HttpGet request = new HttpGet(ENDPOINT2);
        HttpResponse response = client1.execute(request);
         
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
         
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
             
        }
         
        JSONObject o = new JSONObject(result.toString());
        
        
        if(response.getStatusLine().getStatusCode() != 200){
            return Response.status(204).build();
        }

        // Getting motivational picture from Storage -> Adapter (Instagram API)
        String ENDPOINT3 = "https://fathomless-journey-7209.herokuapp.com/introsde/storage/getPicMotivation";
        DefaultHttpClient client3 = new DefaultHttpClient();
        HttpGet request3 = new HttpGet(ENDPOINT3);
        HttpResponse response3 = client3.execute(request3);
        
        BufferedReader rd3 = new BufferedReader(new InputStreamReader(response3.getEntity().getContent()));
        StringBuffer result3 = new StringBuffer();
        String line3 = "";
        
        while ((line3 = rd3.readLine()) != null) {
            result3.append(line3);
        }

          

        JSONObject o3 = new JSONObject(result3.toString());
        
        if(response3.getStatusLine().getStatusCode() != 200){
            return Response.status(204).build();
        }
        
         // Getting quote motivation from Storage -> Adapter (Quote on design API)
        String ENDPOINT4 = "https://fathomless-journey-7209.herokuapp.com/introsde/storage/getQuoteMotivation";
        DefaultHttpClient client4 = new DefaultHttpClient();
        HttpGet request4 = new HttpGet(ENDPOINT4);
        HttpResponse response4 = client4.execute(request4);
        
        BufferedReader rd4 = new BufferedReader(new InputStreamReader(response4.getEntity().getContent()));
        StringBuffer result4 = new StringBuffer();
        String line4 = "";
        
        while ((line4 = rd4.readLine()) != null) {
            result4.append(line4);
        }
    

        JSONObject o4 = new JSONObject(result4.toString());
        
        if(response4.getStatusLine().getStatusCode() != 200){
            System.out.println("IN");
            return Response.status(204).build();
        }
        
                String textXml = "";
        textXml = "<updatedHP>";
        textXml += "<measureValueUpdated>"+ls.getValue()+"</measureValueUpdated>";
        textXml += "<measureName>"+ls.getMeasureName()+"</measureName>";
        textXml += "</updatedHP>";
        
        textXml += "<comparisonInfo>";
        textXml += "<result>"+o.getJSONObject("comparisonInfo").getString("result")+"</result>";
        textXml += "<measure>"+o.getJSONObject("comparisonInfo").getString("measure")+"</measure>";
        textXml += "<goalValue>"+o.getJSONObject("comparisonInfo").getInt("goalValue")+"</goalValue>";
        textXml += "<lifeStatusValue>"+o.getJSONObject("comparisonInfo").getInt("lifeStatusValue")+"</lifeStatusValue>";
        textXml += "</comparisonInfo>";
        
        textXml += "<resultInfo>";
        textXml += "<picture_url>"+o3.getString("picture_url")+"</picture_url>";
        textXml += "<quote>"+o4.getString("quote")+"</quote>";
        textXml += "</resultInfo>";
        
        JSONObject xmlJSONObj = XML.toJSONObject(textXml);
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
        
        System.out.println(jsonPrettyPrintString);
        
        return Response.ok(jsonPrettyPrintString).build();   
    }
    
    
    @PUT
    @Path("/updateGoal")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateGoal(Goal goal) throws IOException {
        
        //Update goal
        String ENDPOINT = "https://fathomless-journey-7209.herokuapp.com/introsde/storage/updateGoal";
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        
        WebTarget service = client.target(ENDPOINT);

        Response res = null;
        String putResp = null;
        
        String updateGoal ="{"
                    + "\"type\": \""+goal.getType()+"\","
                    + "\"value\": \""+goal.getValue()+"\","
                    + "\"idGoal\" : \""+goal.getIdGoal()+"\"}";
        
        res = service.request(MediaType.APPLICATION_JSON_TYPE).put(Entity.json(updateGoal));
        putResp = res.readEntity(String.class);
        
        if(res.getStatus() != 200 ){
            return Response.status(400).build();
        }
        
        return Response.ok(goal).build();

    }
}

    



















