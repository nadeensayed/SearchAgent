package code;

public class Ship extends Cells{

    boolean wrecked;
    int passengerNum;
    int blackboxDamage;
    boolean blackboxRetrievable;

    public Ship(int num)
    {
        wrecked = false;
        passengerNum = num;
        blackboxDamage = 0;
        blackboxRetrievable = false;

    }

    public Ship(int num, boolean w, boolean r, int damage)
    {
        wrecked = w;
        passengerNum = num;
        blackboxDamage = damage;
        blackboxRetrievable = r;

    }

    public String toString()
    {
        return "[Ship(" + passengerNum + ")]";
    }

    @Override
    public Cells clone() throws CloneNotSupportedException {
        Ship cloned = (Ship)super.clone();
        return cloned;
    }
}
