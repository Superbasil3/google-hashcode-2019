package google.hash_code_2019;


import google.hash_code_2019.model.Photo;
import google.hash_code_2019.model.Transitions;
import google.hash_code_2019.model.Tags;

import java.util.HashMap;
import java.util.Map;

public class Simulation {


  Map<Integer, Photo> mapPhoto = new HashMap<>();
  Map<String, Tags> repartitionTags = new HashMap<>();

  public int simulate() {
      int score = 0;

      int maxinterestFactor = 0;

    System.out.println("Number of tags :" + repartitionTags.size());
      repartitionTags.values().stream().sorted().forEach(tag->System.out.println("Tag : " + tag.name + ", iteration " + tag.iteration));

      for (Photo p1 : mapPhoto.values()) {
        Transitions s1 = new Transitions(p1);
        for (Photo p2 : mapPhoto.values()) {
          if(p1 == p2){
            continue;
          }
          Transitions s2 = new Transitions(p2);
          int interest_factor = interest_factor(s1, s2);
          maxinterestFactor = Math.max(maxinterestFactor, interest_factor);
        }
      }

      return score;
  }

  public static int interest_factor(Transitions s1, Transitions s2) {
    int common_tags = 0;
    int tags_in_s1_but_not_in_s2 = 0;
    int tags_in_s2_but_not_in_s2 = 0;
    for (String t : s1.getTags()) {
      if (s2.getTags().contains(t)) {
        common_tags++;
      } else {
        tags_in_s1_but_not_in_s2++;
      }
    }
    for (String t : s2.getTags()) {
      if (!s1.getTags().contains(t)) {
        tags_in_s2_but_not_in_s2++;
      }
    }
    return Math.min(Math.min(common_tags, tags_in_s1_but_not_in_s2), tags_in_s2_but_not_in_s2);
  }

  public void addPhoto(Photo photo) {
    mapPhoto.put(photo.idPhoto, photo);
    addTagRepartition(photo);
  }

  public void addTagRepartition(Photo photo) {
    photo.tags.stream().forEach(tag -> {
          if (repartitionTags.containsKey(tag)) {
            repartitionTags.get(tag).addPhoto(photo.idPhoto);
          } else {
            repartitionTags.put(tag, new Tags(tag,photo.idPhoto));
          }
        }
    );
  }
}
