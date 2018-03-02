package services;

import java.util.Random;

public class ContentService {
    private static Random random;
    public int getContent() {
        return random.nextInt();
    }
}
