package com.prudprudi4.dictionary;

public enum WordType {
    NOUN("существительное"),
    ADJECTIVE("прилагательное"),
    VERB("глагол"),
    ADVERB("наречие"),
    PRONOUN("местоимение");

    private String type;

    private WordType(String type) {
        this.type = type;
    }

    String getType() {
        return type;
    }
}
