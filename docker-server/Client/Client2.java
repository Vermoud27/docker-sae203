import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;

public class Client2 {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    private JFrame frame;
    private JTextArea textArea;
    private JTextField textField;
    private JTextField usernameField;

    public void start() {
        frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JLabel usernameLabel = new JLabel("Nom:");
        usernameField = new JTextField(20);
        JPanel usernamePanel = new JPanel();
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(BorderLayout.NORTH, usernamePanel);

        textField = new JTextField(20);
        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        inputPanel.add(BorderLayout.CENTER, textField);
        inputPanel.add(BorderLayout.EAST, sendButton);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(BorderLayout.CENTER, scrollPane);
        frame.add(BorderLayout.SOUTH, inputPanel);

        connectToServer();
        listenForServerMessages();

        frame.setVisible(true);
    }

    private void connectToServer() {
        try {
            socket = new Socket(HOST, PORT);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connection au serveur");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForServerMessages() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        String message = reader.readLine();
                        if (message == null) {
                            break;
                        }
                        textArea.append(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                        System.out.println("Deconnecter du serveur");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void sendMessage() {
        String username = usernameField.getText();
        String message = textField.getText();
        textField.setText("");
        writer.println(username + " > " + message);
    }

    public static void main(String[] args) {
        new Client2().start();
    }
}
