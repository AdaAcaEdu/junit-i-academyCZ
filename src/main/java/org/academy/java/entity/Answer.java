package org.academy.java.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Answer {

    //<editor-fold desc="Attributes">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Size(max = 200)
    private String text;

    private boolean textMarkup = false;

    private boolean correct = false;

    private boolean chosen = false;

    @JsonIgnore
    @ManyToOne
    Question question = null;
    //</editor-fold>

    //<editor-fold desc="Getters&Setters">
    public long getId() {
        return id;
    }

    public Answer setId(long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public Answer setText(String text) {
        this.text = text;
        return this;
    }

    public boolean isTextMarkup() {
        return textMarkup;
    }

    public Answer setTextMarkup(boolean textMarkup) {
        this.textMarkup = textMarkup;
        return this;
    }

    public boolean isCorrect() {
        return correct;
    }

    public Answer setCorrect(boolean correct) {
        this.correct = correct;
        return this;
    }

    public boolean isChosen() {
        return chosen;
    }

    public Answer setChosen(boolean chosen) {
        this.chosen = chosen;
        return this;
    }

    @JsonIgnore
    public Question getQuestion() {
        return question;
    }

    @JsonProperty
    public Answer setQuestion(Question question) {
        this.question = question;
        return this;
    }
    //</editor-fold>

    //<editor-fold desc="Equals&HashCode">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;

        Answer answer = (Answer) o;

        if (id != answer.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
    //</editor-fold>
}
