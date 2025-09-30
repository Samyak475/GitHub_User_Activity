import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.swing.*;
import java.util.stream.Stream;

public class AppClass  {
    public static final  void main(String [] args) throws IOException {
        Scanner input = new Scanner(System.in);

        String userName = input.nextLine();
        String url = "https://api.github.com/users/";
                url+=userName;
                url+="/events";
        URI  apiUrl = URI.create(url);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                                .uri(apiUrl)
                                .header("name","samyak")
                .GET().build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response ;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper obj = new ObjectMapper();
        List<GitExtract> data = obj.readValue(response.body(), new TypeReference<List<GitExtract>>() {
        });;
    for(GitExtract dt : data){
        if(dt.getType().compareTo("PushEvent")==0 && dt.getPayload()!=null &&dt.getPayload().getCommits() !=null){
            System.out.println("Here for repository "+dt.getRepo().getName()+"Printing all the commits ***************************");
           for(Commits commits:dt.getPayload().getCommits()){
               System.out.println("User "+dt.getActor().getLogin()+" has done commit with message "+ commits.getMessage());
           }
        }


    }


    }


}
