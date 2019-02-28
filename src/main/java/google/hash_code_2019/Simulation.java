package google.hash_code_2019;


import google.hash_code_2019.model.Photo;

import java.util.HashMap;
import java.util.Map;

public class Simulation {


  Map<Long,Photo> mapPhoto;

  public int simulate() {
    int score = 0;


    return score;
  }

  public void addPhoto(Photo photo ){
      if(mapPhoto == null){
        mapPhoto = new HashMap<>();
      }
      mapPhoto.put(photo.id,photo);

  }

}
