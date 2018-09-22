package com.swingy;

import com.swingy.ui.controller.HeroPosition;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MainConsolRun {

    private int sizeMap;
    private int heroPisitionX;
    private int heroPisitionY;
    private  int map[][];

    public MainConsolRun(int mapSize, int heroPisitionX, int getHeroPisitionY) {
        this.sizeMap = mapSize;
        this.heroPisitionX = heroPisitionX;
        this.heroPisitionY = getHeroPisitionY;
    }

    public HeroPosition runConsolFromGui() {
        createMatrix();
        userMove();
        HeroPosition heroPosition = new HeroPosition(heroPisitionX, heroPisitionY);
        System.out.println("You can go back to GUI");
        return heroPosition;
    }

    public static void main(String[] argv) {
        MainConsolRun consol = new MainConsolRun(10, 5, 5);
        consol.createMatrix();
        consol.userMove();
    }

    public void createMatrix() {

        map = new int[sizeMap][sizeMap];

        map[heroPisitionX][heroPisitionY] = 2;
        for (int i = 1; i <= 3 * sizeMap / 2; i++) {
            int enemyX = ThreadLocalRandom.current().nextInt(0, sizeMap);
            int enemyY = ThreadLocalRandom.current().nextInt(0, sizeMap);
            if (map[enemyX][enemyY] == 0) {
                map[enemyX][enemyY] = 1;
            } else if (i > 2) i--;
        }

        for (int i = 0; i < sizeMap; i++) {
            for (int j = 0; j < sizeMap; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("1.Move Up\n2.Move Down\n3.Move Right\n4.Move Left\nPress x to quit\n");
    }

    public void userMove() {

        String userInput = "";
        Scanner scan = new Scanner(System.in);
        while (!userInput.equals("x")) {
            userInput = scan.nextLine();
            switch (userInput) {
                case "1":
                    map[heroPisitionX][heroPisitionY] = 0;
                    heroPisitionX -= 1;
                    recreateMatrix();
                    break;
                case "2":
                    map[heroPisitionX][heroPisitionY] = 0;
                    heroPisitionX += 1;
                    recreateMatrix();
                    break;
                case "3":
                    map[heroPisitionX][heroPisitionY] = 0;
                    heroPisitionY += 1;
                    recreateMatrix();
                    break;
                case "4":
                    map[heroPisitionX][heroPisitionY] = 0;
                    heroPisitionY -= 1;
                    recreateMatrix();
                    break;
            }
        }

    }

    public void recreateMatrix() {

        map[heroPisitionX][heroPisitionY] = 2;
        for (int i = 0; i < sizeMap; i++) {
            for (int j = 0; j < sizeMap; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.print("1.Move Up\n2.Move Down\n3.Move Right\n4.Move Left\nPress x to quit\n");
    }
}

