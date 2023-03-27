package btvn.model;

/**
 *
 * @author tritranmn2
 */
public class Word {
    private String word;
    private String meaning;
    private boolean isFavorite;
    public Word(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        this.isFavorite = false;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }
    
    

    @Override
    public String toString() {
        return word + " : " + meaning;
    }
}

