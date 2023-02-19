package code;

import java.util.ArrayList;
import java.util.List;

public class searchProblem {

    int m;
    int n;
    int maxCapacity;
    State initial;

    public searchProblem(){};

    public searchProblem(int m, int n, int maxCapacity, State initial){
        this.m = m;
        this.n = n;
        this.maxCapacity = maxCapacity;
        this.initial = initial;
    }

    public State getInitialState(){
        return initial;
    }

    public boolean isGoal(State state){
        if(state.passengersLeft==0 && state.blackboxesLeft==0 && state.passengersCarried == 0) return true;

        return false;
    }

    public List<Actions> getOperators(State state){
        ArrayList<Actions> operators = new ArrayList<Actions>();

        Cells cell = state.matrix[state.cgX][state.cgY];

        if (cell instanceof Ship && state.passengersCarried < maxCapacity && ((Ship) cell).passengerNum!=0){
            operators.add(Actions.PICKUP);
        }

        else if(cell instanceof Ship && ((Ship) cell).blackboxRetrievable){
            operators.add(Actions.RETRIEVE);
        }

        else if (cell instanceof Station && state.passengersCarried!=0) {
            operators.add(Actions.DROP);
        }

        else {
            if(state.cgX > 0)
                operators.add(Actions.UP);

            if(state.cgX < n-1)
                operators.add(Actions.DOWN);

            if(state.cgY > 0)
                operators.add(Actions.LEFT);

            if(state.cgY < m-1)
                operators.add(Actions.RIGHT);

        }
        return operators;
    }

    public State getNextState(State state, Actions operator) throws CloneNotSupportedException {

        State next_state = new State(state.cgX, state.cgY, state.passengersCarried, state.passengersLeft, state.blackboxesLeft,
                cloneMap(state.matrix), state.pathCost[0], state.pathCost[1], state.blackboxesTaken, state.passengersSaved);

        switch (operator) {
            case UP:
                next_state.cgX--;
                break;
            case DOWN:
                next_state.cgX++;
                break;
            case LEFT:
                next_state.cgY--;
                break;
            case RIGHT:
                next_state.cgY++;
                break;
            case RETRIEVE:
                ((Ship)next_state.matrix[next_state.cgX][next_state.cgY]).blackboxRetrievable = false;
                next_state.blackboxesLeft--;
                next_state.blackboxesTaken++;
                break;
            case PICKUP:
                int passengersAllowed = maxCapacity - next_state.passengersCarried;
                Ship ship = ((Ship)next_state.matrix[next_state.cgX][next_state.cgY]);
                if(passengersAllowed < ship.passengerNum){
                    ship.passengerNum -= passengersAllowed;
                    next_state.passengersCarried += passengersAllowed;
                    next_state.passengersLeft -= passengersAllowed;
                    next_state.passengersSaved += passengersAllowed;
                }
                else{
                    next_state.passengersCarried += ship.passengerNum;
                    next_state.passengersLeft -= ship.passengerNum;
                    next_state.passengersSaved += ship.passengerNum;
                    ship.passengerNum = 0;
                    ship.wrecked = true;
                    ship.blackboxRetrievable = true;
                    next_state.blackboxesLeft++;
                }
                break;
            case DROP:
                next_state.passengersCarried = 0;
                break;
            default:
                break;
        }
        for(int i = 0; i <next_state.matrix.length; i++){
            for(int j = 0; j<next_state.matrix[i].length; j++){
                if(next_state.matrix[i][j] instanceof Ship){
                    Ship ship = (Ship)next_state.matrix[i][j];
                    if(ship.blackboxRetrievable){
                        ship.blackboxDamage++;
                        if(ship.blackboxDamage > 19){
                            next_state.blackboxesLeft--;
                            ship.blackboxRetrievable = false;
                            next_state.pathCost[1]++;
                        }
                    }
                    else if(ship.wrecked == false){
                        ship.passengerNum--;
                        next_state.pathCost[0]++;
                        next_state.passengersLeft--;
                        if(ship.passengerNum == 0) {
                            ship.wrecked = true;
                            ship.blackboxDamage++;
                            ship.blackboxRetrievable = true;
                            next_state.blackboxesLeft++;
                        }
                    }
                }
            }
        }
        return next_state;
    }

    public Cells[][] cloneMap(Cells[][] map) throws CloneNotSupportedException {
        Cells[][] clone = new Cells[n][m];
        for (int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++)
                if(map[i][j] != null)
                    clone[i][j] = map[i][j].clone();
        }
        return clone;
    }
}
