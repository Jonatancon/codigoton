package reto.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ClientWs {

    public String descryptCodeClient(String codeEncrypt) {
        Response response = null;
        WebTarget webTarget;
        Client client = ClientBuilder.newClient();
        StringBuilder responseJson = null;

        try{
            webTarget = client.target("https://test.evalartapp.com/extapiquest/code_decrypt/" + codeEncrypt);
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            response = invocationBuilder.get();

            responseJson = new StringBuilder( response.readEntity(String.class));
        }catch (Exception e) {
            System.out.println("Error al llamar el WS");
        }finally {
            if (response != null) {
                response.close();
            }
            if (client != null) {
                client.close();
            }
        }

        responseJson.deleteCharAt(0);
        responseJson.deleteCharAt(responseJson.length()-1);

        return responseJson.toString();
    }
}
