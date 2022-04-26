package it.polimi.ingsw.client;


import com.google.gson.Gson;
import it.polimi.ingsw.comunication.CloudCardStatus;
import it.polimi.ingsw.comunication.DashboardStatus;
import it.polimi.ingsw.comunication.IslandStatus;
import it.polimi.ingsw.comunication.TextMessage;
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

    private final BufferedReader socketIn;

    public ClientOut(BufferedReader socketIn) {
        this.socketIn = socketIn;
    }

    private void draw(String student) {
        if (Objects.equals(student, "EMPTY")) {
            System.out.print("^^^");
        } else if (Objects.equals(student, "CYAN")) {
            System.out.print(CYAN + "+++");
        } else if (Objects.equals(student, "MAGENTA")) {
            System.out.print(MAGENTA + "+++");
        } else if (Objects.equals(student, "YELLOW")) {
            System.out.print(YELLOW + "+++");
        } else if (Objects.equals(student, "RED")) {
            System.out.print(RED + "+++");
        } else if (Objects.equals(student, "GREEN")) {
            System.out.print(GREEN + "+++");
        }
        System.out.print(ANSI_RESET);

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
                TextMessage message = gson.fromJson(socketLine, TextMessage.class);
                if (Objects.equals(message.type, "msg")) {
                    System.out.println(message.message);
                } else if (Objects.equals(message.type, "islands")) {
                    IslandStatus[] statuses = gson.fromJson(message.message, IslandStatus[].class);
                    System.out.println("Islands");
                    for (IslandStatus islandStatus : statuses) {
                        System.out.println("============");
                        System.out.println("=Island #" + islandStatus.id);
                        System.out.println("=ID Group: " + islandStatus.idGroup);
                        System.out.println("=Is conquered: " + islandStatus.islandConquered);
                        System.out.println("=Presence: " + islandStatus.presenceMN);
                        System.out.println("=Students: ");
                        for (String student : islandStatus.students) {
                            draw(student);
                            System.out.println(" ");
                        }
                        System.out.println("=Available towers: " + islandStatus.towerColor);

                    }
                } else if (Objects.equals(message.type, "dashboard")) {
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
                            System.out.println(teacher);
                        }
                        System.out.println(" ");
                        System.out.println("Towers available: " + dashboardStatus.towers);


                    }

                } else if (Objects.equals(message.type, "cloudCard")) {
                    CloudCardStatus[] cloudCardStatuses = gson.fromJson(message.message, CloudCardStatus[].class);
                    for (CloudCardStatus cloudCardStatus: cloudCardStatuses){
                        System.out.println("=====CLoudCard: ");
                        for (String student: cloudCardStatus.students){
                            draw(student);
                            System.out.println(" ");
                        }

                    }
                } else if (Objects.equals(message.type, "quit")) {
                    System.out.println(message.message);
                    break;
                }
            }

        }

    }

}

