package Neptune.handlers;


import Neptune.models.Television;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class InputFileHandler {
    public static List<Television> parseFile(String filePath) throws FileNotFoundException, NullPointerException, JSONException {
        if (filePath == null) {
            throw new FileNotFoundException("Property `last_used_nodes_file` not found");
        }

        List<Television> tvList = new ArrayList<Television>();

        /**
         * Open file as stream
         */
        FileInputStream in = new FileInputStream(filePath);

        JSONTokener tokener = new JSONTokener(in);
        JSONArray root = new JSONArray(tokener);
        JSONArray nodes = root.getJSONObject(0).getJSONArray("values");

        /**
         * Parse JSON into objects
         */
        for (int i = 0; i < nodes.length(); i++) {
            Television workingTelevision = null;
            String name = nodes.getJSONObject(i).getString("name");
            System.out.println("Name: " + name);
            String ip = nodes.getJSONObject(i).getString("ip");
            String port = nodes.getJSONObject(i).getString("port");
            String username = nodes.getJSONObject(i).getString("username");
            String password = nodes.getJSONObject(i).getString("password");

            try {
                workingTelevision = new Television(name, ip, port, username, password);
            } catch (UnknownHostException e) {
                System.out.println("Error parsing IP address: " + ip);
                e.printStackTrace();
            }

            if (workingTelevision != null) {
                tvList.add(workingTelevision);
            }
        }

        return tvList;
    }
}
