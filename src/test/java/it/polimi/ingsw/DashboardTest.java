package it.polimi.ingsw;

import it.polimi.ingsw.model.Dashboard;
import junit.framework.TestCase;

public class DashboardTest extends TestCase {

    public void testDrawDashboard() {
        Dashboard testD = new Dashboard();
        testD.drawDashboard();
    }
}