package com.prudprudi4.dictionary;

public enum WordType {
    NOUN("существительное"),
    ADJECTIVE("прилагательное"),
    VERB("глагол"),
    ADVERB("наречие"),
    PRONOUN("местоимение"),
    PARTICLE("частица");

    private final String type;

    String getType() {
        return type;
    }

    WordType(String type) {
        this.type = type;
    }
}
