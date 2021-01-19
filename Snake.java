package com.codegym.games.snake;
import com.codegym.engine.cell.*;
import java.util.ArrayList;
import java.util.List;


// Let op, dit is project dat ik met CodeGym lessen te volgen :) 


public class Snake {
    // Emoji's voor slang's hoofd en lichaam (puntjes)
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";

// Lichaam wordt gerepresentateerd via een ArrayList
    private List<GameObject> snakeParts = new ArrayList<>();
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

// De slang
    public Snake(int x, int y) {
        GameObject first = new GameObject(x, y);
        GameObject second = new GameObject(x + 1, y);
        GameObject third = new GameObject(x + 2, y);
        snakeParts.add(first);
        snakeParts.add(second);
        snakeParts.add(third);
    }

// for loop ++ lengte van slang 

    public void draw(Game game) {
        Color color = isAlive ? Color.BLACK : Color.RED;

        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;
            game.setCellValueEx(part.x, part.y, Color.NONE, sign, color, 75);
        }
    }

// Richtingen slang, ENUM Direction
    public void setDirection(Direction direction) {
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if ((this.direction == Direction.UP || this.direction == Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }

        if ((direction == Direction.UP && this.direction == Direction.DOWN)
                || (direction == Direction.LEFT && this.direction == Direction.RIGHT)
                || (direction == Direction.RIGHT && this.direction == Direction.LEFT)
                || (direction == Direction.DOWN && this.direction == Direction.UP))
            return;

        this.direction = direction;
    }

//Verplaatsen van de Appel

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x >= SnakeGame.WIDTH
                || newHead.x < 0
                || newHead.y >= SnakeGame.HEIGHT
                || newHead.y < 0) {
            isAlive = false;
            return;
        }

        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        }

        snakeParts.add(0, newHead);

        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
        } else {
            removeTail();
        }
    }
// verplaatsen van de hoofd van de slang
    public GameObject createNewHead() {
        GameObject head = snakeParts.get(0);
        int headX = head.x;
        int headY = head.y;
        int newHeadX = headX;
        int newHeadY = headY;
        if (direction == Direction.LEFT) {
            newHeadX--;
        } else if (direction == Direction.RIGHT) {
            newHeadX++;
        } else if (direction == Direction.UP) {
            newHeadY--;
        } else {
            newHeadY++;
        }

        return new GameObject(newHeadX, newHeadY);
    }
//verwijderen van de staart, laaste bolletje
    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject part : snakeParts) {
            if (part.x == gameObject.x && part.y == gameObject.y) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }
}
