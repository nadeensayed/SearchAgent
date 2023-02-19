package code;

public class State {

    int cgX;
    int cgY;
    int [] pathCost = new int[2];
    int passengersCarried;
    int passengersLeft;
    int blackboxesLeft;
    int blackboxesTaken;
    int passengersSaved;

    Cells[][] matrix;

    public State(int x, int y, int pl, Cells[][] matrix)
    {
        cgX = x;
        cgY = y;
        passengersCarried = 0;
        pathCost[0] = 0;
        pathCost[1] = 0;
        passengersLeft = pl;
        blackboxesLeft = 0;
        blackboxesTaken = 0;
        this.matrix = matrix;
        passengersSaved = 0;
    }

    public State(int x, int y, int pc, int pl, int bl, Cells[][] matrix, int deaths, int bb, int taken, int saved)
    {
        cgX = x;
        cgY = y;

        passengersCarried = pc;

        passengersLeft = pl;
        blackboxesLeft = bl;

        this.matrix = matrix;
        blackboxesTaken = taken;

        pathCost[0] = deaths;
        pathCost[1] = bb;

        passengersSaved = saved;
    }

}
