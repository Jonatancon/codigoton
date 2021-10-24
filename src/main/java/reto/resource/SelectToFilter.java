package reto.resource;

import reto.models.Client;
import reto.service.ClientService;
import reto.utils.ClientWs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectToFilter extends ClientWs{

    private FileReader fr = null;
    private BufferedReader br = null;
    private Map<String, String> filtrosGenerales = new HashMap<>();
    private ClientService clientService = new ClientService();
    String  CANCELADA = "CANCELADA";
    StringBuilder selecionados = new StringBuilder();
    StringBuilder result = new StringBuilder();

    private String query = "WITH orden AS(\n" +
            "\t SELECT *,\n" +
            "        ROW_NUMBER() OVER( PARTITION BY male ORDER BY male) rn\n" +
            "    FROM client\n" +
            ")\n" +
            "SELECT c.id, c.code , c.male, c.type, c.location, c.company, c.encrypt, SUM(a.balance) as total\n" +
            "FROM orden c JOIN account a ON c.id = a.client_id\n" +
            "GROUP BY a.client_id\n" +
            "HAVING (COUNT(*) > 1 OR COUNT(*) = 1)\n";

    private String finalQuery = "ORDER BY rn, total DESC;";

    public String consulta(Map<String, String> filters) {
        String perosnal = query;

        if (filters.containsKey("TC")) {
            perosnal+= String.format("AND (c.type = %s)\n", filters.get("TC"));
        }
        if (filters.containsKey("UG")) {
            perosnal+= String.format("AND (c.location = %s)\n", filters.get("UG"));
        }
        if (filters.containsKey("RI")) {
            perosnal+= String.format("AND (total >= %s)\n", filters.get("RI"));
        }
        if (filters.containsKey("RF")) {
            perosnal+= String.format("AND (total <= %s)\n", filters.get("RF"));
        }
        return  perosnal+= finalQuery;
    }

    public void cleanFile(File file) {
        Map<String, String> filtroMesas = new HashMap<>();
        try{
            fr = new FileReader (file);
            br = new BufferedReader(fr);
            String line;

            line = br.readLine();

            while(line != null) {
                String[] filtro;

              if (line.equals("<General>")) {
                  while ((line = br.readLine()).charAt(1) != 'M') {
                      filtro = line.split(":");
                      filtrosGenerales.put(filtro[0], filtro[1]);
                  }
                  filtroMesas.putAll(filtrosGenerales);
              }
                if (line.charAt(0) == '<' && line.charAt(1) == 'M') {
                    String mesa = line;
                    while ((line = br.readLine()) != null) {

                        if (line.charAt(0) == '<' && line.charAt(1) == 'M'){
                           break;
                        }

                        if (line.charAt(0) != '<' && line.charAt(1) != 'M') {
                            filtro = line.split(":");

                            if (filtroMesas.containsKey(filtro[0])) {
                                filtroMesas.put(filtro[0], filtro[1]);
                            }else{
                                filtroMesas.put(filtro[0], filtro[1]);
                            }
                        }
                    }
                    convertData(filtroMesas, mesa);
                    filtroMesas.clear();
                    filtroMesas.putAll(filtrosGenerales);
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }

    private void convertData(Map<String, String> filtros, String mesa) {
        String query = consulta(filtros);
        int count = 1;

        List<Client> clients = clientService.findAllByQuery(query);

        if (clients.isEmpty() || clients.size() < 4) {
            selecionados.append(mesa).append("\n").append(CANCELADA).append("\n");
        }else {
            clients.forEach(client -> {
                if (client.getEncrypt() == 1) {
                    client.setCode(descryptCodeClient(client.getCode()));
                }
            });

            clients = filterForCompany(clients);
            if (clients.isEmpty() || clients.size() < 4) {
                selecionados.append(mesa).append("\n").append(CANCELADA).append("\n");
            }else{

                clients = filterForMale(clients);
                if (clients.isEmpty() || clients.size() < 4) {
                    selecionados.append(mesa).append("\n").append(CANCELADA).append("\n");
                }else{
                    selecionados.append(mesa).append("\n");
                    for (Client client: clients) {
                        if (count == 8)
                            break;

                        result.append(client.getCode()).append(",");
                        count++;
                    }
                    result.deleteCharAt(result.length()-1);
                    selecionados.append(result);
                    System.out.println(selecionados);
                }
            }
        }
    }

    private List<Client> filterForCompany(List<Client> clientList) {
        Map<String, String> company = new HashMap<>();
        List<Client> newListClient = new ArrayList<>();

        clientList.forEach(client -> {
            if (!company.containsKey(client.getCompany())){
                company.put(client.getCompany(), client.getCode());
                newListClient.add(client);
            }
        });
        return newListClient;
    }

    private List<Client> filterForMale(List<Client> clientList) {
        int male = 0;
        int women = 0;
        int diference;

        for (Client client: clientList) {
            if (client.getMale() == 1){
                male++;
            }else {
                women++;
            }
        }

        diference = Math.abs(male-women);
        boolean mayor = male > women;

        for (int i = clientList.size()-1; i > 0; i--){
            if (mayor) {
                if (clientList.get(i).getMale() == 1) {
                    clientList.remove(i);
                    diference--;
                }
            }else{
                if (clientList.get(i).getMale() == 0) {
                    clientList.remove(i);
                    diference--;
                }
            }
            if (diference == 0)
                break;
        }
        return clientList;
    }

}
