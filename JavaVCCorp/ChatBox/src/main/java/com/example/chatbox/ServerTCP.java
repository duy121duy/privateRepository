package com.example.chatbox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args) throws IOException {
        String sentence_from_client;
        String sentence_to_client;

        //Tạo socket server, chờ tại cổng '6543'
        ServerSocket welcomeSocket = new ServerSocket(6543);

        while (true) {
            //chờ yêu cầu từ client
            Socket connectionSocket = welcomeSocket.accept();

            //Tạo input stream, nối tới Socket
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            //Tạo outputStream, nối tới socket
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            //Đọc thông tin từ socket
            sentence_from_client = inFromClient.readLine();

            sentence_to_client = sentence_from_client + " (Server accepted!)" + '\n';
            //ghi dữ liệu ra socket
            outToClient.writeBytes(sentence_to_client);
        }
    }

    private static class ServiceThread extends Thread {
        private Socket socketOfServer;

        public ServiceThread(Socket socketOfServer) {
            this.socketOfServer = socketOfServer;

            // Log
            System.out.println("New connection at " + socketOfServer);
        }

        @Override
        public void run() {
            try {
                // Mở luồng vào ra trên Socket tại Server.
                BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

                while (true) {
                    // Đọc dữ liệu tới server (Do client1 gửi tới).
                    String line = is.readLine();
                    // Ghi vào luồng đầu ra của Socket tại Server.
                    // (Nghĩa là gửi tới Client).
                    os.write(">> " + line);
                    // Kết thúc dòng
                    os.newLine();
                    // Đẩy dữ liệu đi
                    os.flush();
                    // Nếu người dùng gửi tới QUIT (Muốn kết thúc trò chuyện).
                    if (line.equals("QUIT")) {
                        os.write(">> OK");
                        os.newLine();
                        os.flush();
                        break;
                    }
                }

            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}


