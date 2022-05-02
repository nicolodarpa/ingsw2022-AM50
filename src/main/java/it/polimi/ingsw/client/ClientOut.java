package it.polimi.ingsw.client;


import com.google.gson.Gson;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Student;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class ClientOut extends Thread {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";

    private final Gson gson = new Gson();
    private TextMessage message;

    private final BufferedReader socketIn;

    public ClientOut(BufferedReader socketIn) {
        this.socketIn = socketIn;
    }

    private void draw(String color) {
        if (Objects.equals(color, "EMPTY")) {
            System.out.print("^^^");
        } else if (Objects.equals(color, "CYAN")) {
            System.out.print(CYAN + "+++");
        } else if (Objects.equals(color, "MAGENTA")) {
            System.out.print(MAGENTA + "+++");
        } else if (Objects.equals(color, "YELLOW")) {
            System.out.print(YELLOW + "+++");
        } else if (Objects.equals(color, "RED")) {
            System.out.print(RED + "+++");
        } else if (Objects.equals(color, "GREEN")) {
            System.out.print(GREEN + "+++");
        }
        System.out.print(ANSI_RESET);

    }

    private void printIslands() {
        IslandStatus[] statuses = gson.fromJson(message.message, IslandStatus[].class);
        System.out.println("Islands");
        for (IslandStatus islandStatus : statuses) {
            if (islandStatus.presenceMN) {
                System.out.print(YELLOW);
            }
            System.out.println("============");
            System.out.println("=Island #" + islandStatus.id);
            System.out.println("=Dimension : " + islandStatus.dimension);
            System.out.println("=Is conquered: " + islandStatus.islandConquered);
            System.out.println("=Owner: " + islandStatus.owner);
            System.out.println("=Towers #: " + islandStatus.towerNumber);
            System.out.println("=Presence of MN: " + islandStatus.presenceMN);
            System.out.println("=Students: ");
            System.out.print(ANSI_RESET);
            for (String student : islandStatus.students) {
                draw(student);
                System.out.println(" ");
            }


        }
    }

    private void printDashboard() {
        DashboardStatus[] dashboardStatuses = gson.fromJson(message.message, DashboardStatus[].class);
        for (DashboardStatus dashboardStatus : dashboardStatuses) {
            System.out.println("============");
            System.out.println("Dashboard of " + dashboardStatus.nameOwner);
            System.out.print("Hall: ");
            for (String student : dashboardStatus.studentsHall) {
                System.out.print("-");
                draw(student);
            }
            System.out.println(" ");
            System.out.println("Classrooms");
            for (int i = 0; i < 5; i++) {

                for (int j = 0; j < 10; j++) {

                    draw(dashboardStatus.studentsClassroom[i][j]);
                    System.out.print("--");
                }
                System.out.println(" ");
            }
            System.out.print("Teacher Table: ");
            for (String teacher : dashboardStatus.teacherTable) {
                System.out.print("-");
                draw(teacher);
            }
            System.out.println(" ");
            System.out.println("Towers available: " + dashboardStatus.towers);


        }
    }

    private void printCloudCard() {
        CloudCardStatus[] cloudCardStatuses = gson.fromJson(message.message, CloudCardStatus[].class);
        for (CloudCardStatus cloudCardStatus : cloudCardStatuses) {
            System.out.println("=====CLoudCard: ");
            for (String student : cloudCardStatus.students) {
                draw(student);
                System.out.println(" ");
            }

        }
    }

    private void printCharacterCards(){
        CharacterCard[] characterCards = gson.fromJson(message.message, CharacterCard[].class);
        for (CharacterCard characterCard: characterCards){
            System.out.println("====");
            System.out.println("Effect: " + characterCard.effect);
            System.out.println("Cost: " + characterCard.cost);
        }
    }

    private void printHall() {
        HallStatus[] hallStatuses = gson.fromJson(message.message, HallStatus[].class);
        for (HallStatus hallStatus : hallStatuses) {
            System.out.println("=====Hall: ");
            for (String student : hallStatus.students) {
                System.out.print("-");
                draw(student);
            }
            System.out.println(" ");


        }
    }

    @Override
    public void run() {
        while (true) {
            String socketLine;
            try {
                socketLine = socketIn.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (socketLine != null) {
                Gson gson = new Gson();
                message = gson.fromJson(socketLine, TextMessage.class);
                if (Objects.equals(message.type, "msg")) {
                    System.out.println(message.message);
                } else if (Objects.equals(message.type, "error")) {
                    System.out.println(RED + message.message + ANSI_RESET);
                } else if (Objects.equals(message.type, "warning")) {
                    System.out.println(YELLOW + message.message + ANSI_RESET);
                } else if (Objects.equals(message.type, "notify")) {
                    System.out.println(GREEN + message.message + ANSI_RESET);
                } else if (Objects.equals(message.type, "characterCards")) {
                    printCharacterCards();
                } else if (Objects.equals(message.type, "islands")) {
                    printIslands();
                } else if (Objects.equals(message.type, "dashboard")) {
                    printDashboard();
                } else if (Objects.equals(message.type, "cloudCard")) {
                    printCloudCard();
                } else if (Objects.equals(message.type, "hall")) {
                    printHall();
                } else if (Objects.equals(message.type, "quit")) {
                    System.out.println(message.message);
                    break;
                }
            }

        }

    }

}

