class DuplicateWordException extends Exception {
    DuplicateWordException(String word) {
        super(String.format("Duplicated word was found: \"%s\"", word));
    }
}
