package worksample;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random r = new Random();
        while (true) {
            switch (r.nextInt(2)) {
                case 0:
                    QuoterInvoker.invoke(new AQuoter());
                case 1:
                    QuoterInvoker.invoke(new BQuoter());
                case 2:
                    QuoterInvoker.invoke(new CQuoter());
            }
        }
    }
}
