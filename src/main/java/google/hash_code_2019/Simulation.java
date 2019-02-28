package google.hash_code_2019;


import google.hash_code_2019.model.Photo;

import java.util.HashMap;
import java.util.Map;

public class Simulation {


  Map<Integer, Photo> mapPhoto = new HashMap<>();

  Map<String,Integer> repartitionTags = new HashMap<>();


  public int simulate() {
    int score = 0;
    return score;
  }

  public void addPhoto(Photo photo) {
    mapPhoto.put(photo.idPhoto, photo);

  }


  public void addTagRepartition(Photo photo) {
    photo.tags.stream().forEach(tag -> {
          if (repartitionTags.containsKey(tag)) {
            repartitionTags.put(tag,repartitionTags.get(tag) +1 );

          } else {
            repartitionTags.put(tag,0);
          }
        }
    );
  }
}
