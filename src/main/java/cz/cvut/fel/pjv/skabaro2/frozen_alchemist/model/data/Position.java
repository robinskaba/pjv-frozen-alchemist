package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position getPositionWithDirection(Direction direction) {
        return switch (direction) {
            case UP -> new Position(this.x, this.y - 1);
            case DOWN -> new Position(this.x, this.y + 1);
            case LEFT -> new Position(this.x - 1, this.y);
            case RIGHT -> new Position(this.x + 1, this.y);
        };
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }
}
