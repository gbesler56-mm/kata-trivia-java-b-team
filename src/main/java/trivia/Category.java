package trivia;

enum Category {

    POP("Pop", 0),
    SCIENCE("Science", 1),
    SPORTS("Sports", 2),
    ROCK("Rock", 3);

    final int mod4;
    final String categoryName;

    Category(String categoryName, int mod4) {
        this.mod4 = mod4;
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
