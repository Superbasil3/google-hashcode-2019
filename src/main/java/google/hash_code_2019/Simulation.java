package google.hash_code_2019;


import google.hash_code_2019.model.Photo;
import google.hash_code_2019.model.Slide;
import google.hash_code_2019.model.Tags;
import google.hash_code_2019.model.Transitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Simulation {


  Map<Integer, Photo> mapPhoto = new HashMap<>();
  List<Photo> horizontalPhotos = new ArrayList<>();
  List<Photo> verticalPhotos = new ArrayList<>();
  List<Slide> allPossibleSlides = new ArrayList<>();
  Map<String, Tags> repartitionTags = new HashMap<>();
  public Transitions transitions= new Transitions();

  public int simulate() {
    int score = 0;

    //System.out.println("Number of tags :" + repartitionTags.size());
    //repartitionTags.values().stream().sorted().forEach(tag -> System.out.println("Tag : " + tag.name + ", iteration " + tag.iteration));

    horizontalPhotos.addAll(mapPhoto.values().stream().filter(p -> p.horizontal).collect(Collectors.toList()));
    verticalPhotos.addAll(mapPhoto.values().stream().filter(p -> !p.horizontal).collect(Collectors.toList()));
    allPossibleSlides.addAll(horizontalPhotos.stream().map(Slide::new).collect(Collectors.toList()));

    for (int i = 0; i < verticalPhotos.size(); i++) {
      for (int j = i + 1; j < verticalPhotos.size(); j++) {
        allPossibleSlides.add(new Slide(verticalPhotos.get(i), verticalPhotos.get(j)));
      }
    }

    Transitions transitions = new Transitions();
    score = findFirstransition(transitions);
    while (!allPossibleSlides.isEmpty()) {
      score += findBestSecondTransition(transitions);
    }
    return score;
  }

  private int findBestSecondTransition(Transitions transitions) {
    int maxinterestFactor = 0;
    Slide bestS2 = null;

    Slide s1 = transitions.transitions.getFirst();
    for (int j = 0; j < allPossibleSlides.size(); j++) {
      Slide s2 = allPossibleSlides.get(j);
      int interest_factor = interest_factor(s1, s2);
      if (maxinterestFactor < interest_factor) {
        maxinterestFactor = interest_factor;
        bestS2 = s2;
      }
    }
    s1 = transitions.transitions.getLast();
    for (int j = 0; j < allPossibleSlides.size(); j++) {
      Slide s2 = allPossibleSlides.get(j);
      int interest_factor = interest_factor(s1, s2);
      if (maxinterestFactor < interest_factor) {
        maxinterestFactor = interest_factor;
        bestS2 = s2;
      }
    }

    transitions.addLast(bestS2);
    // Remove not possible slides
    allPossibleSlides.remove(bestS2);
    List<Slide> toRemove = new ArrayList<>();
    for (Slide s : allPossibleSlides) {
      if (s.photo2 != null && (s.photo2 == bestS2.photo2)) {
        toRemove.add(s);
      }
    }
    allPossibleSlides.removeAll(toRemove);

    return maxinterestFactor;

  }

  private void removeAlreadyTakenSlides(Slide bestS1, Slide bestS2) {
    // Remove not possible slides
    allPossibleSlides.remove(bestS1);
    allPossibleSlides.remove(bestS2);
    List<Slide> toRemove = new ArrayList<>();
    for (Slide s : allPossibleSlides) {
      if (s.photo2 != null && (s.photo2 == bestS1.photo2 || s.photo2 == bestS2.photo2)) {
        toRemove.add(s);
      }
    }
    allPossibleSlides.removeAll(toRemove);
  }

  private int findFirstransition(Transitions transitions) {
    int maxinterestFactor = 0;
    Slide bestS1 = null;
    Slide bestS2 = null;

    for (int i = 0; i < allPossibleSlides.size(); i++) {
      for (int j = i + 1; j < allPossibleSlides.size(); j++) {
        Slide s1 = allPossibleSlides.get(i);
        Slide s2 = allPossibleSlides.get(j);
        int interest_factor = interest_factor(s1, s2);
        if (maxinterestFactor < interest_factor) {
          maxinterestFactor = interest_factor;
          bestS1 = s1;
          bestS2 = s2;
        }
      }
    }

    transitions.addFirst(bestS1);
    transitions.addLast(bestS2);

    removeAlreadyTakenSlides(bestS1, bestS2);

    return maxinterestFactor;
  }

  public static int interest_factor(Slide s1, Slide s2) {
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
            repartitionTags.put(tag, new Tags(tag, photo.idPhoto));
          }
        }
    );
  }


  public int fakeSimulate() {
    Slide verticalSlide = new Slide();
    for (Photo photo : mapPhoto.values()) {
      if (photo.horizontal) {
        transitions.addLast(new Slide(photo));
      } else {
        if(verticalSlide.photo1 != null){
          verticalSlide.addPhoto2(photo);
          transitions.addLast(verticalSlide);
          verticalSlide = new Slide();
        } else {
          verticalSlide.addPhoto1(photo);
        }
      }
    }
    return 0;
  }
}


