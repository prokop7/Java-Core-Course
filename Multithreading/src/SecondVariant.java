public class SecondVariant implements Algorithm {
    @Override
    public boolean handle(String s) throws UnexpectedSymbolException {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'e')
                throw new UnexpectedSymbolException();
        }
        return false;
    }
}
