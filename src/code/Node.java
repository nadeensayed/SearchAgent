package code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Node implements Comparable{

    State state;
    Node parent;
    Actions operator;
    int depth;

    private int comparable;

    public Node(State state) {
        this.state = state;
        this.depth = 0;
        this.parent = null;
    }

    public Node(State state, Node parent, Actions operator) {
        this(state);
        this.parent = parent;
        this.operator = operator;
        this.depth = parent.depth + 1;
    }

    public int getDeaths() {
        return state.pathCost[0];
    }

    public int getDestroyed() {
        return state.pathCost[1];
    }

    public int getPathCost() {
        return state.pathCost[0] + state.pathCost[1];
    }

    public List<Node> getPathFromRoot() {
        List<Node> path = new ArrayList<Node>();
        Node current = this;
        while (!current.isRoot()) {
            path.add(0, current);
            current = current.getParent();
        }

        path.add(0, current);
        return path;
    }

    public State getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public Actions getOperator() {
        return operator;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public int getDepth() {
        return depth;
    }

    public void visualizePath() {
        List<Node> nodes = getPathFromRoot();
        for (int i = 0; i < nodes.size(); i++) {
            System.out.println(nodes.get(i).getOperator());
            System.out.println("AGENT AT (" + nodes.get(i).state.cgX + "," + nodes.get(i).state.cgY+ ")");
            Cells [][] draw = nodes.get(i).state.matrix;
            for (int x = 0; x<draw.length; x++) {
                for (int y = 0; y<draw[x].length; y++) {
                    if(draw[x][y] == null) System.out.print("[       ]");
                    else System.out.print(draw[x][y].toString());
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public boolean repeated(int x, int y, int p, int b, int s){
        if(this.state.cgX == x && this.state.cgY == y &&
                this.state.passengersCarried == p && this.state.blackboxesTaken == b && this.state.passengersSaved == s) return true;
        return false;
    }

    public String plan() {
        String plan = "";
        List<Node> nodes = getPathFromRoot();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getOperator() != null) {
                switch (nodes.get(i).getOperator()) {
                    case UP:
                        plan += "up,";
                        break;
                    case DOWN:
                        plan += "down,";
                        break;
                    case LEFT:
                        plan += "left,";
                        break;
                    case RIGHT:
                        plan += "right,";
                        break;
                    case PICKUP:
                        plan += "pickup,";
                        break;
                    case DROP:
                        plan += "drop,";
                        break;
                    case RETRIEVE:
                        plan += "retrieve,";
                        break;
                    default:
                        break;
                }
            }
        }
        return plan.substring(0, plan.length()-1)+ ";" + state.pathCost[0] + ";" + state.blackboxesTaken + ";";
    }

    @Override
    public int compareTo(Object o) {
        int x;
        int y;

        switch (this.comparable) {
            // GREEDY 1
            case 0:
                x = this.heuristicFunction(1);
                y = ((Node)o).heuristicFunction(1);
                return Integer.compare(x, y);

            // GREEDY 2
            case 1:
                x = this.heuristicFunction(2);
                y = ((Node)o).heuristicFunction(2);
                return Integer.compare(x, y);

            // A* 1
            case 2:
                x = this.getDeaths() + this.heuristicFunction(1);
                y = ((Node)o).getDeaths() + ((Node)o).heuristicFunction(1);
                int check1 = Integer.compare(x, y);
                if(check1 == 0) return Integer.compare(this.getDestroyed(), ((Node)o).getDeaths());
                return check1;

            // A* 2
            case 3:
                x = this.getDeaths() + this.heuristicFunction(2);
                y = ((Node)o).getDeaths() + ((Node)o).heuristicFunction(2);
                int check2 = Integer.compare(x, y);
                if(check2 == 0) return Integer.compare(this.getDestroyed(), ((Node)o).getDeaths());
                return check2;

            default:
                return 0;
        }
    }

    public int heuristicFunction(int h){
        int heuristic = 0;
        //Cells [][] matrix = this.state.matrix;

        if(h==1) {
            heuristic = this.state.passengersLeft;
        }
        else {
            heuristic = this.state.blackboxesLeft;
        }


        return heuristic;
    }


    public static Collection<Node> setSearchStrategy(Collection<Node> nodes, int strategy) {
        for (Node o : nodes) {
            o.comparable = strategy;
        }
        return nodes;
    }
}
