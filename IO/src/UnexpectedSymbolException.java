class UnexpectedSymbolException extends Exception{
    UnexpectedSymbolException(char c) {
        super(String.format("Unexpected character was found: \"%s\"", c));
    }
}
