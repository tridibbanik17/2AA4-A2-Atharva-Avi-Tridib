package ca.mcmaster.se2aa4.island.team033;

public class BatteryState {

    private int charge;
    private final int warning = 25; // Let when the remaining battery charge level is 25%, a warning signal is sent.

    public BatteryState(int charge) {
        this.charge = charge;
    }

    public int getBatteryState() {
        return charge;
    }

    public void decreaseCharge(int cost) {
        charge = charge - cost;

    }

    public boolean getWarning() {
        return this.charge == this.warning;
    }
}