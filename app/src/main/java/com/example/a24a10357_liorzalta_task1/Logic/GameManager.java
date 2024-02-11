package com.example.a24a10357_liorzalta_task1.Logic;

import android.util.Log;

import com.example.a24a10357_liorzalta_task1.Model.Entity;
import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    private int cols;
    private int rows;
    private int lives;  // max lives
    private int hits;
    private int obstaclesInterval; // interval between obstacles creation
    private int intervalCounter;
    private Random rand = new Random();
    private ArrayList<Entity> entities; // holds coordinates of player and existing obstacles

    public GameManager(){
        this(3, 6, 3, 4);
    }

    public GameManager(int lives, int rows, int cols, int obstaclesInterval){
        this.cols = cols;
        this.rows = rows;
        this.lives = lives;
        this.hits = 0;

        this.obstaclesInterval = obstaclesInterval;
        this.intervalCounter = 0;

        this.entities = new ArrayList<>();
        this.entities.add(new Entity()
                .setCords(rows-1, 1));  // create the player entity
    }

    public int[][] getEntitiesCords() {
        int[][] cords = new int[entities.size()][2];

        for (int i = 0; i < cords.length; i++){
            cords[i][0] = entities.get(i).getCords()[0];
            cords[i][1] = entities.get(i).getCords()[1];
        }
        return cords;
    }

    public int getHits() {
        return hits;
    }

    public boolean isLost(){
        return lives == hits;
    }

    public void generateObstacle(){
        entities.add(new Entity()
                .setCords(0, rand.nextInt(cols)));

        // reset counter for next obstacle
        intervalCounter = 0;
    }

    public boolean moveObstacles(){
        boolean isHit = false;

        Entity player = entities.get(0);
        int[] playerCords = player.getCords();

        for (int i = 1; i < entities.size(); i++){

            Entity obstacle = entities.get(i);
            int[] obstacleCords = obstacle.getCords();
            Log.d("cords before obst", obstacleCords[0] + ", " + obstacleCords[1]);

            obstacleCords[0]++;

            Log.d("cords after obst", obstacleCords[0] + ", " + obstacleCords[1]);

            //obstacle hits the player
            if (obstacleCords[0] == rows - 1 && obstacleCords[1] == playerCords[1]){
                hits++;
                isHit = true;
            }
            //obstacle out of board
            else if (obstacleCords[0] == rows){
                entities.remove(i);
                i--;
            }
        }
        intervalCounter++;
        if (intervalCounter == obstaclesInterval)
            generateObstacle();
        return isHit;
    }

    public void movePlayerRight(){
        if (entities.get(0).getCords()[1] < cols - 1)
            entities.get(0).getCords()[1]++;
    }

    public void movePlayerLeft(){
        if (entities.get(0).getCords()[1] > 0)
            entities.get(0).getCords()[1]--;
    }

    public void resetGame(){
        this.hits = 0;
        this.intervalCounter = 0;
        this.entities = new ArrayList<>();
        this.entities.add(new Entity()
                .setCords(rows-1, 1));
    }
}
