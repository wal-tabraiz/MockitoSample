package com.mockitosample;

public class Human {
    public String name;
    public Puppy puppy;

    public Human(String name) {
        this.name = name;
    }

    public void buyPuppy(String name){
        puppy = Puppy.createPuppy(name, this);
    }

    public void walkWithPuppy() {
        puppy.goOnWalk(15);
    }

    public void isSoHappy() {
        System.out.println("Yay!");
    }
}