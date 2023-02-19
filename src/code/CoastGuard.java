package code;

import java.util.ArrayList;

public class CoastGuard extends searchProblem{

    public static String GenGrid()
    {
        int m = (int)Math.floor(Math.random()*(4-1+1)+1);
        int n = (int)Math.floor(Math.random()*(4-1+1)+1);
        int cells = m * n;

        ArrayList<Integer> grid = new ArrayList<Integer>();
        for(int i = 0; i < cells ; i++)
            grid.add(i);

        int capacity = (int)Math.floor(Math.random()*(100-30+1)+30);

        int posCG = (int)Math.floor(Math.random()*(grid.size()));
        int cgX = grid.get(posCG) / m;
        int cgY = grid.get(posCG) % m;
        grid.remove(posCG);

        int rand = (int)Math.floor(Math.random()*(2));
        String stations = "";
        String ships = "";

        if(rand == 0)
        {
            int noOfStations = (int)Math.floor(Math.random()*(grid.size()-1) + 1);
            for(int i = 0; i < noOfStations ; i++){
                int x = (int)Math.floor(Math.random()*(grid.size()));
                int sX = grid.get(x) / m;
                int sY = grid.get(x) % m;
                stations += sX + "," + sY + ",";
                grid.remove(x);
            }
            stations = stations.substring(0, stations.length() - 1);

            int noOfShips = (int)Math.floor(Math.random()*(grid.size()) + 1);
            for(int i = 0; i < noOfShips ; i++){
                int x = (int)Math.floor(Math.random()*(grid.size()));
                int sX = grid.get(x) / m;
                int sY = grid.get(x) % m;
                int passengers = (int)Math.floor(Math.random()*(100)+1);
                ships += sX + "," + sY + "," + passengers + ",";
                grid.remove(x);
            }
            ships = ships.substring(0, ships.length() - 1);
        }

        else
        {
            int noOfShips = (int)Math.floor(Math.random()*(grid.size()-1) + 1);
            for(int i = 0; i < noOfShips ; i++){
                int x = (int)Math.floor(Math.random()*(grid.size()));
                int sX = grid.get(x) / m;
                int sY = grid.get(x) % m;
                int passengers = (int)Math.floor(Math.random()*(100)+1);
                ships += sX + "," + sY + "," + passengers + ",";
                grid.remove(x);
            }
            ships = ships.substring(0, ships.length() - 1);

            int noOfStations = (int)Math.floor(Math.random()*(grid.size()) + 1);
            for(int i = 0; i < noOfStations ; i++){
                int x = (int)Math.floor(Math.random()*(grid.size()));
                int sX = grid.get(x) / m;
                int sY = grid.get(x) % m;
                stations += sX + "," + sY + ",";
                grid.remove(x);
            }
            stations = stations.substring(0, stations.length() - 1);
        }
        return m + "," + n + ";" + capacity + ";" + cgX + "," + cgY + ";" +
                stations + ";" + ships;
    }

    public static String solve(String grid, String strategy, boolean visualize) throws CloneNotSupportedException {
        String [] parts = grid.split(";");

        String [] q = parts[0].split(",");
        int m = Integer.parseInt(q[0]); // COLUMNS
        int n = Integer.parseInt(q[1]); // ROWS

        Cells [][]  matrix = new Cells[n][m];

        int maxCapacity = Integer.parseInt(parts[1]); // MAX CAPACITY

        String [] w = parts[2].split(",");
        int cgX = Integer.parseInt(w[0]); // AGENT X
        int cgY = Integer.parseInt(w[1]); // AGENT Y

        String [] stations = parts[3].split(",");
        for(int i = 0; i <= stations.length-2 ; i+=2){
            int x = Integer.parseInt(stations[i]);
            int y = Integer.parseInt(stations[i+1]);
            matrix [x][y] = new Station();
        }

        String [] ships = parts[4].split(",");
        int totalPassengers = 0;
        for(int i = 0; i <= ships.length-3 ; i+=3){
            int x = Integer.parseInt(ships[i]);
            int y = Integer.parseInt(ships[i+1]);
            int pass = Integer.parseInt(ships[i+2]);
            totalPassengers += pass;
            matrix [x][y] = new Ship(pass);
        }

        searchProblem sp = new searchProblem(m, n, maxCapacity, new State(cgX, cgY, totalPassengers, matrix));
        searchProcedure sw = new searchProcedure();
        Node result = null;

        switch (strategy) {
            case "BF":
                result = sw.BF(sp);
                break;
            case "DF":
                result = sw.DF(sp);
                break;
            case "ID":
                result = sw.ID(sp);
                break;
            case "GR1":
                result = sw.GR1(sp);
                break;
            case "GR2":
                result = sw.GR2(sp);
                break;
            case "AS1":
                result = sw.AS1(sp);
                break;
            case "AS2":
                result = sw.AS2(sp);
                break;
            default:
                System.out.println("Please enter a valid search strategy");
                break;
        }

        if (!(result==null)) {
            if (visualize) {
                result.visualizePath();
            }
        }

        return result.plan() + sw.previous.size();
    }

    public static void main(String[]args) throws CloneNotSupportedException {
        String x = "7,5;100;3,4;2,6,3,5;0,0,4,0,1,8,1,4,77,1,5,1,3,2,94,4,3,46;";
        String y = solve(x, "AS2", false);
        System.out.println(y);
    }
}
