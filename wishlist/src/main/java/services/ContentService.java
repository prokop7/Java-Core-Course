package services;

import java.util.Random;

public class ContentService {
    private static Random random = new Random();

    public int getContent() {
        return random.nextInt();
    }
}
