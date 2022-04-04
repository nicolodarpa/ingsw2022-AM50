package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class EchoServerClientHandler extends Thread {

    private final Socket socket;
    private static ArrayList<EchoServerClientHandler> threadList;
    private final ClientUI clientUI;
    private Game game;


    public EchoServerClientHandler(Socket socket, ArrayList<EchoServerClientHandler> threads, Game game) throws IOException {
        this.socket = socket;
        this.threadList = threads;
        this.game = game;
        this.clientUI = new ClientUI(socket);

    }


    public void run() {

        try {
            Scanner in = new Scanner(socket.getInputStream());
            String name = in.nextLine();
            boolean check = true;
            while (check) {
                switch (LoginManager.login(name, game)) {
                    case 0:
                        //PlayersList.addPlayer(name);
                        if (game.getCurrentNumberOfPlayers() == 1) {
                            clientUI.print("Welcome " + name + ", choose number of players, 2 or 3 allowed:");
                            String numPlayers;
                            numPlayers = in.nextLine();
                            while (!Objects.equals(numPlayers, "2") && !Objects.equals(numPlayers, "3")) {
                                clientUI.print("Please enter a valid number: 2 or 3 allowed");
                                numPlayers = in.nextLine();
                            }
                            game.setNumberOfPlayers(Integer.parseInt(numPlayers));
                            clientUI.print("Waiting for other " + (game.getNumberOfPlayers() - 1) + " players");
                        } else {
                            clientUI.print("Welcome " + name);
                        }

                        check = false;
                        break;
                    case 2:
                        clientUI.print("Name already in use by another player, chose a different name:");
                        name = in.nextLine();
                        break;
                    case 1:
                        clientUI.print("Max number of players reached");
                        check = false;
                        in.close();
                        clientUI.close();
                        socket.close();
                        break;
                }
            }


            while (true) {
                String line = in.nextLine();
                if (line.equals("quit")) {
                    break;
                } else {
                    clientUI.print("Received: " + line);
                }
            }

            in.close();
            clientUI.close();
            socket.close();
            game.removePlayer(name);


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printToAllClients(String outputString) {
        /**for (EchoServerClientHandler cl : threadList) {
         cl.out.println(outputString);
         }**/
        System.out.println("print to all, game starting");
    }

}
