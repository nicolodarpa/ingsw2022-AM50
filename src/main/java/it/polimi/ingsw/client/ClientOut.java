package it.polimi.ingsw.client;


import com.google.gson.Gson;
import it.polimi.ingsw.comunication.DashboardStatus;
import it.polimi.ingsw.comunication.IslandStatus;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.model.Dashboard;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.Teacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class ClientOut extends Thread {

    private final BufferedReader socketIn;

    public ClientOut(BufferedReader socketIn) {
        this.socketIn = socketIn;
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
                            System.out.println("-" + student + "-");
                        }
                        System.out.println("=Tower: " + islandStatus.towerColor);

                    }
                } else if (Objects.equals(message.type, "dashboard")) {
                    DashboardStatus[] dashboardStatuses = gson.fromJson(message.message, DashboardStatus[].class);
                    for (DashboardStatus dashboardStatus : dashboardStatuses) {
                        System.out.println("============");
                        System.out.println("Dashboard of " + dashboardStatus.nameOwner);
                        System.out.print("Hall: ");
                        for (String student : dashboardStatus.studentsHall) {
                            System.out.print("-");
                            System.out.println(student);
                        }
                        System.out.println(" ");
                        System.out.println("Classrooms");
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 10; j++) {

                                System.out.print(dashboardStatus.studentsClassroom[i][j] + "--");
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

                } else if (Objects.equals(message.type, "quit")) {
                    System.out.println(message.message);
                    break;
                }
            }

        }

    }

}

