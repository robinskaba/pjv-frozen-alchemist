package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data;

import java.util.Objects;

/**
 * Represents a position in a 2D space with x and y coordinates.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Constructs a new Position object with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Increments the x-coordinate by 1.
     */
    public void incrementX() {
        x++;
    }

    /**
     * Increments the y-coordinate by 1.
     */
    public void incrementY() {
        y++;
    }

    /**
     * Decrements the x-coordinate by 1.
     */
    public void decrementX() {
        x--;
    }

    /**
     * Decrements the y-coordinate by 1.
     */
    public void decrementY() {
        y--;
    }

    /**
     * Creates a copy of the current Position object.
     *
     * @return A new Position object with the same x and y coordinates.
     */
    public Position copy() {
        return new Position(x, y);
    }

    /**
     * Compares this Position object to another object for equality.
     *
     * @param o The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    /**
     * Generates a hash code for the Position object.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a string representation of the Position object.
     *
     * @return A string in the format "Position{x=x, y=y}".
     */
    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }
}