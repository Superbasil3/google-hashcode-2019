package google.hash_code_2019;


import google.hash_code_2019.model.Photo;

import java.util.HashMap;
import java.util.Map;

public class Simulation {


  Map<Integer, Photo> mapPhoto = new HashMap<>();

  Map<String, Integer> repartitionTags = new HashMap<>();


  public int simulate() {
    int score = 0;


    System.out.println("number of tags " + repartitionTags.size());

    for (Map.Entry<String, Integer> tag : repartitionTags.entrySet()) {
      System.out.println("tag :" + tag.getKey() + ", occurence " + tag.getValue());

    }

    return score;
  }

  public void addPhoto(Photo photo) {
    mapPhoto.put(photo.idPhoto, photo);
    addTagRepartition(photo);

  }


  public void addTagRepartition(Photo photo) {
    photo.tags.stream().forEach(tag -> {
          if (repartitionTags.containsKey(tag)) {
            repartitionTags.put(tag, repartitionTags.get(tag) + 1);

          } else {
            repartitionTags.put(tag, 1);
          }
        }
    );
  }
}
