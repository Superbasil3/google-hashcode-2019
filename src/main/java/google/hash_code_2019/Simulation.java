package google.hash_code_2019;


import com.google.common.collect.Sets;
import google.hash_code_2019.model.*;
import sun.security.krb5.internal.ccache.Tag;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Simulation {


  Map<Integer, Photo> mapPhoto = new HashMap<>();
  List<Photo> horizontalPhotos = new ArrayList<>();
  List<Photo> verticalPhotos = new ArrayList<>();
  List<Slide> allPossibleSlides = new ArrayList<>();
  Map<String, Tags> repartitionTags = new HashMap<>();
  public Transitions transitions= new Transitions();

  public void prepareAndStat(){
    List<Tags> listTag = repartitionTags.values().stream().sorted(Tags::compareTo).collect(Collectors.toList());
    long occurence = -1L;
    int nbrTag = 0;
//    for(Tags tag : listTag){
//      if(occurence == -1L){
//        occurence = tag.iteration;
//        nbrTag = 1;
//      } else if (occurence != tag.iteration){
//        System.out.println(nbrTag + " Tags present " + occurence + "time");
//        occurence = tag.iteration;
//        nbrTag = 1;
//      } else {
//        nbrTag ++;
//      }
//    }
    System.out.println(nbrTag + " Tags present " + occurence + "time");


    horizontalPhotos.addAll(mapPhoto.values().stream().filter(p -> p.horizontal).collect(Collectors.toList()));
    verticalPhotos.addAll(mapPhoto.values().stream().filter(p -> !p.horizontal).collect(Collectors.toList()));
    System.out.println("Number of tags :" + repartitionTags.size());


    System.out.println("Vertical: " + verticalPhotos.size());
    System.out.println("Horizontal: " + horizontalPhotos.size());
  }

  public int simulate() {
    int score = 0;



    allPossibleSlides.addAll(horizontalPhotos.stream().map(Slide::new).collect(Collectors.toList()));
    for (int i = 0; i < verticalPhotos.size(); i++) {
      for (int j = i + 1; j < verticalPhotos.size(); j++) {
        allPossibleSlides.add(new Slide(verticalPhotos.get(i), verticalPhotos.get(j)));
      }
    }
    System.out.println("All possible slides " + allPossibleSlides.size());

    int i = 0;
        score = findFirstransition(transitions);
        while (!allPossibleSlides.isEmpty()) {
          i++;
          if (i > 1000) {
            System.out.println("Remaining " + allPossibleSlides.size());
            i = 0;
          }
            score += findBestSecondTransition(transitions);
        }
        return score;
    }


  private int findBestSecondTransition(Transitions transitions) {
    int maxinterestFactor = 0;
    Slide bestS2 = null;

    final Slide s1 = transitions.transitions.getFirst();

    Calcul calcul = allPossibleSlides.parallelStream().map(s2 -> {
      int interest_factor = interest_factor(s1, s2);
      return new Calcul(s1, s2, interest_factor);
    }).max((c1, c2) -> Integer.max(c1.factor, c2.factor)).get();
    bestS2 = calcul.s2;

    /*for (int j = 0; j < allPossibleSlides.size() - 1; j++) {
      Slide s2 = allPossibleSlides.get(j);
      int interest_factor = interest_factor(s1, s2);
      if (maxinterestFactor < interest_factor) {
        maxinterestFactor = interest_factor;
        bestS2 = s2;
      }
    }
    s1 = transitions.transitions.getLast();
    for (int j = 0; j < allPossibleSlides.size() - 1; j++) {
      Slide s2 = allPossibleSlides.get(j);
      int interest_factor = interest_factor(s1, s2);
      if (maxinterestFactor < interest_factor) {
        maxinterestFactor = interest_factor;
        bestS2 = s2;
      }
    }*/

    if (bestS2 == null) {
      System.out.println("NULL");
    }
    transitions.addLast(bestS2);
    // Remove not possible slides
    allPossibleSlides.remove(bestS2);
    List<Slide> toRemove = new ArrayList<>();
    for (Slide s : allPossibleSlides) {
      if (s.photo1 == bestS2.photo2) {
        toRemove.add(s);
      }
      if (s.photo1 == bestS2.photo1) {
        toRemove.add(s);
      }
      if (s.photo2 != null && (s.photo2 == bestS2.photo2)) {
        toRemove.add(s);
      }
      if (s.photo2 != null && (s.photo2 == bestS2.photo1)) {
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
      if (s.photo1 == bestS1.photo2 || s.photo1 == bestS2.photo2) {
        toRemove.add(s);
      }
      if (s.photo1 == bestS1.photo1 || s.photo1 == bestS2.photo1) {
        toRemove.add(s);
      }
      if (s.photo2 != null && (s.photo2 == bestS1.photo2 || s.photo2 == bestS2.photo2)) {
        toRemove.add(s);
      }
      if (s.photo2 != null && (s.photo2 == bestS1.photo1 || s.photo2 == bestS2.photo1)) {
        toRemove.add(s);
      }
    }
    allPossibleSlides.removeAll(toRemove);
  }

  private int findFirstransition(Transitions transitions) {
    int maxinterestFactor = 0;
    Slide bestS1 = null;
    Slide bestS2 = null;


    Slide s1 = allPossibleSlides.get(new Random().nextInt(allPossibleSlides.size()));
    System.out.println(s1);

    for (int j = 0; j < allPossibleSlides.size() - 1; j++) {
      Slide s2 = allPossibleSlides.get(j);
      if (s1 == s2) {
        continue;
      }

      int interest_factor = interest_factor(s1, s2);
      if (maxinterestFactor < interest_factor) {
        maxinterestFactor = interest_factor;
        bestS1 = s1;
        bestS2 = s2;
      }
    }

        /*for (int i = 0; i < allPossibleSlides.size(); i++) {
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

            System.out.println("First iteration " + i);
        }*/

    if (bestS1 == null) {
      System.out.println("NULL");
    }

    if (bestS2 == null) {
      System.out.println("NULL");
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
    if (s1 == null || s2 == null) {
      System.out.println("null");
    }
     common_tags = Sets.intersection(s1.getTags(), s2.getTags()).size();
    if (common_tags == 0) {
      return 0;
    }
    tags_in_s1_but_not_in_s2 = s1.getTags().size() - common_tags;
    tags_in_s2_but_not_in_s2 = s2.getTags().size() - common_tags;
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


    Transitions random = new Transitions();
    int size = transitions.transitions.size();
    while (size > 0) {
      Integer randomInteger = ThreadLocalRandom.current().nextInt(0, transitions.transitions.size());
      random.addLast(transitions.transitions.get(randomInteger));
      transitions.transitions.remove(transitions.transitions.get(randomInteger));
      size = transitions.transitions.size();
    }
    transitions = random;

    return 0;
  }

  public void countScore() {
    int score = 0;
    for(int i = 0; i < transitions.transitions.size() -2 ; i++){
      Slide slide1 = transitions.transitions.get(i);
      Slide slide2 = transitions.transitions.get(i+1);
      score += interest_factor(slide1,slide2);
    }
    System.out.println("Total Score " + score);
  }
}


