import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppClass  {
    public static final  void main(String [] args) throws IOException {
        System.out.println("github-activity ");
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
//        System.out.println(response.body());
    for(GitExtract dt : data){
        System.out.println(dt.getType());
        System.out.println(dt.getPayload().getCommits());
        System.out.println(dt.getActor().getLogin());
        System.out.println(dt.getRepo().getName());
        System.out.println("*****************************************************************************************");

        if(dt.getType().compareTo("PushEvent")==0 && dt.getPayload()!=null &&dt.getPayload().getCommits() !=null){
            System.out.println("Here for repository "+dt.getRepo().getName()+"Printing all the commits ***************************");
           for(Commits commits:dt.getPayload().getCommits()){
               System.out.println("User "+dt.getActor().getLogin()+" has done commit with message "+ commits.getMessage());
           }
        }
        else if(dt.getType().compareTo("CreateEvent")==0){
            System.out.println("New "+dt.getPayload().getRef_type()+" is created for "+dt.getRepo().getName()+" with name "+dt.getPayload().getRef());
        }


    }


    }


}
