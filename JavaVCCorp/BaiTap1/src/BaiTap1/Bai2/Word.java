package BaiTap1.Bai2;

public class Word {
    private String name;
    private int totalCharacters;

    public Word() {
    }

    public Word(String name, int totalCharacters) {
        this.name = name;
        this.totalCharacters = totalCharacters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalCharacters() {
        return totalCharacters;
    }

    public void setTotalCharacters(int totalCharacters) {
        this.totalCharacters = totalCharacters;
    }
}
