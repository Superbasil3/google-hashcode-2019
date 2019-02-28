package google.hash_code_2019.model;

import java.util.ArrayList;
import java.util.List;

public class Slide {
  List<Slide> slide;

  public Slide() {
    this.slide = new ArrayList<>();
  }

  public void addPictureEnd(Photo photo){
    slide.add(photo);
  }

  public void addPictureBeging(Photo photo){
    slide.add(photo);
  }






}
