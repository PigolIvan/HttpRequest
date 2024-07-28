import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class GetAndPostRequest {
    public static void main(String[] args) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(new URI("https://my-json-server.typicode.com/typicode/demo/posts"))
                .GET()
                .header("Accept", "Application/json")
                .build();

            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            if(response1.statusCode() > 299){
                System.out.println("Помилка. Статус код: " + response1.statusCode());
            } else {
                List<Post> result = mapper.readValue(response1.body(), new TypeReference<List<Post>>() {});
                for (Post data : result){
                    System.out.println(data.toString());
                }
            }

        //POST request
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(new URI("https://my-json-server.typicode.com/typicode/demo/posts"))
                .POST(HttpRequest.BodyPublishers.ofString("[{\"id\": 4, \"title\": \"Post 4\"}]"))
                .header("Content-type", "application/json")
                .build();

            HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        if(response2.statusCode() > 299){
            System.out.println("Помилка. Статус код: " + response2.statusCode());
        } else {
            List<Post> result = mapper.readValue(response2.body(), new TypeReference<List<Post>>() {});
            for (Post data : result){
                System.out.println(data.toString());
            }
        }
    }
}