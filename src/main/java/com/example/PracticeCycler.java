package com.example;

import java.util.ArrayList;

public class PracticeCycler {

    private ArrayList<String> songHolder=new ArrayList<String>();
    private ArrayList<String> exerciseHolder=new ArrayList<String>();
    private ArrayList<String> scaleHolder=new ArrayList<String>();

    PracticeCycler(){
        exerciseHolder.add("Play all Major scales through circle of fifths.");
        exerciseHolder.add("Play all Major arpeggios through circle of fifths.");
        exerciseHolder.add("Play all Minor scales through circle of fifths.");
        exerciseHolder.add("Play ii-V-I for all keys around circle of fifths.");
        exerciseHolder.add("Left hand steady rhythm, while running 1/1, 1/2, 1/4 and 1/8th notes in right hand.");
        exerciseHolder.add("C major arpeggios.");

        scaleHolder.add("C Major");
        scaleHolder.add("G Major");
        scaleHolder.add("D Major");
        scaleHolder.add("A Major");
        scaleHolder.add("E Major");
        scaleHolder.add("B Major");
    }


    public String cycle(int type){
        if (type == 0){
            return exerciseHolder.get((int) getRandomIntegerBetweenRange(0, exerciseHolder.size() - 1));
        }

        if (type == 1){
            return songHolder.get((int) getRandomIntegerBetweenRange(0, songHolder.size() - 1));
        }

        if (type == 2){
            return scaleHolder.get((int) getRandomIntegerBetweenRange(0, scaleHolder.size() - 1));
        }

        throw new IllegalArgumentException("Please use a valid practicer object type...");

    }

    public void addToDataHolder(String text, int type){
        if (type == 0){
            exerciseHolder.add(text);
        }

        if (type == 1){
            songHolder.add(text);
        }

    }

    // helper method.
    private static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
}

//TODO
// Set up practicer objects as a priortiy queue
