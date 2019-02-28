package google.hash_code_2019;


import google.hash_code_2019.model.Photo;

import java.util.HashSet;
import java.util.Set;

public class Simulation {


  Set<Photo> mapPhoto;

  public int simulate() {
    int score = 0;


    return score;
  }

  public void addPhoto(Photo photo ){
      if(mapPhoto == null){
        mapPhoto = new HashSet<>();
      }
      mapPhoto.add(photo);

  }

}
