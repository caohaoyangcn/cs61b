package creatures;

import java.awt.Color;

import huglife.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    private double repEnergyRetained = 0.5;
    private double repEnergyGiven = 0.5;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public Clorus () {
        this(1);
    }

    public Color color() {
        return new Color(r, g, b);
    }

    /**
     * Called when this creature moves.
     */
    public void move() {
        energy -= 0.03;
    }

    /**
     * Called when this creature attacks C.
     */
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Called when this creature chooses replicate.
     * Must return a creature of the same type.
     */
    public Clorus replicate() {
        double babyEnergy = energy * repEnergyGiven;
        energy = energy * repEnergyRetained;
        return new Clorus(babyEnergy);
    }

    /**
     * Called when this creature chooses stay.
     */
    public void stay() {
        energy -= 0.01;
    }

    /**
     * Returns an action. The creature is provided information about its
     * immediate NEIGHBORS with which to make a decision.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {

        boolean hasEmptySquare = false;
        boolean plipNearBy = false;
        Deque<Direction> targetsDirections = new ArrayDeque<>();
        Deque<Direction> emptySquareDirections = new ArrayDeque<>();
        for (Direction d: neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                hasEmptySquare = true;
                emptySquareDirections.add(d);
            }

            if (neighbors.get(d).name().equals("plip")) {
                plipNearBy = true;
                targetsDirections.add(d);
            }
        }

        // rule 1: if there are no empty squares,
        // STAY even if there are Plips nearby
        if (!hasEmptySquare) {
            return new Action(Action.ActionType.STAY);
        }

        // rule 2: if any Plips are seen, ATTACK one of them randomly
        if (plipNearBy) {
            return new Action(Action.ActionType.ATTACK,
                    HugLifeUtils.randomEntry(targetsDirections));
        }

        // rule 3: if Clorus has more than 1 unit of energy, REPLICATE to
        // a random empty square
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE,
                    HugLifeUtils.randomEntry(emptySquareDirections));
        }

        // rule 4: default - MOVE to a random empty square
        return new Action(Action.ActionType.MOVE,
                HugLifeUtils.randomEntry(emptySquareDirections));
    }

}
