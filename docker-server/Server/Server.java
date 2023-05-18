import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    
    private static final int PORT = 8080;
    
    private List<PrintWriter> clientWriters = new ArrayList<>();
    
    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur sur le port : " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau Client Connecter: " + clientSocket.getInetAddress().getHostName());
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                clientWriters.add(writer);
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private class ClientHandler implements Runnable {
        
        private Socket clientSocket;
        
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (true) {
                    String message = reader.readLine();
                    if (message == null) {
                        break;
                    }
                    System.out.println("Message recu: " + message);
                    broadcast(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Client deconnecter: " + clientSocket.getInetAddress().getHostName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private void broadcast(String message) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }
    
    public static void main(String[] args) {
        new Server().start();
    }
}
