package google.hash_code_2019.model;

import java.util.*;

public class Transitions {
  public LinkedList<Slide> transitions;

  public Transitions() {
    this.transitions = new LinkedList<>();
  }

  public void addFirst(Slide slide){
    transitions.addFirst(slide);
  }

  public void addLast(Slide slide){
    transitions.addLast(slide);
  }






}
