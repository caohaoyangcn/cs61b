package creatures;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.*;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.stay();
        assertEquals(1.96, c.energy(), 0.01);
        Plip p = new Plip(1.5);
        c.attack(p);
        assertEquals(3.46, c.energy(), 0.01);

    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(3.46);
        Clorus baby = (Clorus) c.replicate();
        assertEquals(1.73, baby.energy(), 0.01);
        assertEquals(1.73, c.energy(), 0.01);
        baby.stay();
        assertEquals(1.72, baby.energy(), 0.01);
        assertNotEquals(baby, c);
    }

    @Test
    public void testChoose() {
        // No empty squares: STAY
        Clorus c = new Clorus(3.46);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action expected = new Action(Action.ActionType.STAY);
        Action actual = c.chooseAction(surrounded);
        assertEquals(expected, actual);
    }

    @Test
    public void testChoose1() {
        // Empty square exists and energy >= 1: REPLICATE itself
        Clorus c1 = new Clorus(3.46);
        HashMap<Direction, Occupant> surrounded1 = new HashMap<Direction, Occupant>();
        surrounded1.put(Direction.TOP, new Empty());
        surrounded1.put(Direction.BOTTOM, new Impassible());
        surrounded1.put(Direction.LEFT, new Impassible());
        surrounded1.put(Direction.RIGHT, new Impassible());
        Action expected1 = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        Action actual1 = c1.chooseAction(surrounded1);
        assertEquals(expected1, actual1);
    }

    @Test
    public void testChoose2() {
        // Empty square exists and energy < 1: MOVE to empty square
        Clorus c1 = new Clorus(0.46);
        HashMap<Direction, Occupant> surrounded1 = new HashMap<Direction, Occupant>();
        surrounded1.put(Direction.TOP, new Empty());
        surrounded1.put(Direction.BOTTOM, new Impassible());
        surrounded1.put(Direction.LEFT, new Impassible());
        surrounded1.put(Direction.RIGHT, new Impassible());
        Action expected1 = new Action(Action.ActionType.MOVE, Direction.TOP);
        Action actual1 = c1.chooseAction(surrounded1);
        assertEquals(expected1, actual1);
    }

    @Test
    public void testChoose3() {
        // Empty square exists and any Plips are seen: ATTACK random Plip
        Clorus c1 = new Clorus(1.46);
        HashMap<Direction, Occupant> surrounded1 = new HashMap<Direction, Occupant>();
        surrounded1.put(Direction.TOP, new Plip());
        surrounded1.put(Direction.BOTTOM, new Empty());
        surrounded1.put(Direction.LEFT, new Impassible());
        surrounded1.put(Direction.RIGHT, new Impassible());
        Action expected1 = new Action(Action.ActionType.ATTACK, Direction.TOP);
        Action actual1 = c1.chooseAction(surrounded1);
        assertEquals(expected1, actual1);
    }

    @Test
    public void testChoose4() {
        // No empty square and any Plips are seen: STAY
        Clorus c1 = new Clorus(0.46);
        HashMap<Direction, Occupant> surrounded1 = new HashMap<Direction, Occupant>();
        surrounded1.put(Direction.TOP, new Plip());
        surrounded1.put(Direction.BOTTOM, new Plip());
        surrounded1.put(Direction.LEFT, new Plip());
        surrounded1.put(Direction.RIGHT, new Plip());
        Action expected1 = new Action(Action.ActionType.STAY);
        Action actual1 = c1.chooseAction(surrounded1);
        assertEquals(expected1, actual1);
    }
}
