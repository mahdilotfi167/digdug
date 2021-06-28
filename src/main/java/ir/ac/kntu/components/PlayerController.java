package ir.ac.kntu.components;

import ir.ac.kntu.models.Player;
import ir.ac.kntu.rigidbody.Vector;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerController {
    private Player player;
    private KeyCode up;
    private KeyCode right;
    private KeyCode down;
    private KeyCode left;
    private KeyCode shoot;

    public PlayerController(Player player, KeyCode up, KeyCode righ, KeyCode down, KeyCode left, KeyCode shoot) {
        this.down = down;
        this.right = righ;
        this.up = up;
        this.left = left;
        this.shoot = shoot;
        this.player = player;
    }

    public void keyHandler(KeyEvent e) {
        KeyCode key = e.getCode();
        int speed = 5;
        if (key == up) {
            player.move(new Vector(0, -1).multiply(player.getSpeed()));
            e.consume();
        }
        if (key == down) {
            player.move(new Vector(0, 1).multiply(player.getSpeed()));
            e.consume();
        }
        if (key == right) {
            player.move(new Vector(1, 0).multiply(player.getSpeed()));
            e.consume();
        }
        if (key == left) {
            player.move(new Vector(-1, 0).multiply(player.getSpeed()));
            e.consume();
        }
        if (key == shoot) {
            player.shoot();
            e.consume();
        }
    }
}
