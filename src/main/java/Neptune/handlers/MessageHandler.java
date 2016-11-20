package Neptune.handlers;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MessageHandler {
	
	private static final int DEFAULT_TIMEOUT = 200;

	/**
	 * 
	 * @param ip - IP of the TV
	 * @param port - Port of the TV
	 * @param timeout_opt - Timeout in milliseconds.  Set to 0 to use default timeout
	 * @param username - NOT IMPLEMENTED
	 * @param password - NOT IMPLEMENTED
	 * @param command - 4 character command
	 * @param parameter - 4 character parameter (must be padded with "space" if shorter than 4 characters)
	 * @return the response
	 */
	public static String sendCommand(InetAddress ip, Integer port, int timeout_opt, String username, String password, String command, String parameter) {
		
		BufferedReader reader;
		BufferedWriter writer;

		int timeout;
		
		String message = new String();
		String response = null;
		
		message = ( command + parameter);

		if (timeout_opt != 0) {		// user has set timeout property
			timeout = timeout_opt;
		} else {						// user has not set timeout property
			timeout = DEFAULT_TIMEOUT;
		}
		
		try {
			
			if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
				//TODO
				System.out.println("Error: Node is requesting username and password of " + username + " : " + password);
				return "Abort: Username/Password feature not yet implemented";
			}
			
			Socket cSocket = new Socket();
			cSocket.connect(new InetSocketAddress(ip, port), timeout);
			
			reader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(cSocket.getOutputStream()));

			writer.write(message);
			writer.write("\r");
			writer.flush();
			
			response =  reader.readLine();
			
			cSocket.close();
			
		} catch (IOException e) {
			return e.getMessage().toString();
		} 
		
		
		return response;
	}
	
}
