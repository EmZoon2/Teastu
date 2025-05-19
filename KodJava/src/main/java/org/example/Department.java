package org.example;

public enum Department
{
    none, // kurs bez wydzialu, dostepny dla wszystkich
    business,
    health,
    history,
    biology,
    psychology,
    engineering,
    it,
    arts,
    education,
    journalism;
    @Override
    public String toString() {
        return super.toString();
    }
}
