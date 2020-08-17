package com.datastore;

import java.util.*;

public class Chords {


    public Map chordStore = new HashMap<String, List<String>>();
    private static String  urlBase = "https://storage.googleapis.com/piano-assistant-static/static/chords/";


    public Chords(){

        this.chordStore.put("a major", Arrays.asList("A Major",urlBase + "a_major_triad.png","three"));
        this.chordStore.put("a minor", Arrays.asList("A Minor",urlBase + "a_minor_triad.png","three"));
        this.chordStore.put("a flat major", Arrays.asList("A Flat Major",urlBase + "aflat_major_triad.png","three"));
        this.chordStore.put("a flat minor", Arrays.asList("A Flat Minor",urlBase + "aflat_minor_triad.png","three"));
        this.chordStore.put("b major", Arrays.asList("B Major",urlBase + "b_major_chord1.png","three"));
        this.chordStore.put("b minor", Arrays.asList("B Minor",urlBase + "b_minor_chord1.png","three"));
        this.chordStore.put("b flat major", Arrays.asList("B Flat Major",urlBase + "bflat_major_triad.png","three"));
        this.chordStore.put("b flat minor", Arrays.asList("B Flat Minor",urlBase + "bflat_minor_triad.png","three"));
        this.chordStore.put("c major", Arrays.asList("C Major",urlBase + "c_major_triad.png","three"));
        this.chordStore.put("c minor", Arrays.asList("C Minor",urlBase + "c_minor_triad.png","three"));
        this.chordStore.put("c flat major", Arrays.asList("C Flat Major",urlBase + "cflat_major_triad1.png","three"));
        this.chordStore.put("c sharp major", Arrays.asList("C Sharp Major",urlBase + "csharp_major_triad.png","three"));
        this.chordStore.put("c sharp minor", Arrays.asList("C Sharp Minor",urlBase + "csharp_minor_triad.png","three"));
        this.chordStore.put("d flat minor", Arrays.asList("D Flat Minor",urlBase + "csharp_minor_triad.png","three"));
        this.chordStore.put("d major", Arrays.asList("D Major",urlBase + "d_major_triad.png","three"));
        this.chordStore.put("d minor", Arrays.asList("D Minor",urlBase + "d_minor_triad.png","three"));
        this.chordStore.put("d flat major", Arrays.asList("D Flat Major",urlBase + "dflat_major_triad.png","three"));
        this.chordStore.put("e major", Arrays.asList("E Major",urlBase + "e_major_triad.png","three"));
        this.chordStore.put("e minor", Arrays.asList("E Minor",urlBase + "e_minor_triad.png","three"));
        this.chordStore.put("e flat major", Arrays.asList("E Flat Major",urlBase + "eflat_major_triad.png","three"));
        this.chordStore.put("e flat minor", Arrays.asList("E Flat Minor",urlBase + "eflat_minor_triad.png","three"));
        this.chordStore.put("f major", Arrays.asList("F Major",urlBase + "f_major_triad.png","three"));
        this.chordStore.put("f minor", Arrays.asList("F Minor",urlBase + "f_minor_triad.png","three"));
        this.chordStore.put("f sharp major", Arrays.asList("F Sharp Major",urlBase + "fsharp_major_triad.png","three"));
        this.chordStore.put("f sharp minor", Arrays.asList("F Sharp Minor",urlBase + "fsharp_minor_triad.png","three"));
        this.chordStore.put("g major", Arrays.asList("G Major",urlBase + "g_major_triad1.png","three"));
        this.chordStore.put("g minor", Arrays.asList("G Minor",urlBase + "g_minor_triad1.png","three"));
        this.chordStore.put("g flat major", Arrays.asList("G Flat Major",urlBase + "gflat_major_triad.png","three"));
    }


    public List getRandomChordObject(){
        Random generator = new Random();
        Object[] values = chordStore.values().toArray();
        return (List) values[generator.nextInt(values.length)];

    }


}
