package google.hash_code_2019;


import com.google.common.collect.Sets;
import google.hash_code_2019.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Simulation {


  static String filename = "a_example";
  Map<Integer, Photo> mapPhoto = new HashMap<>();
  List<Photo> horizontalPhotos = new ArrayList<>();
  List<Photo> verticalPhotos = new ArrayList<>();
  List<Slide> allPossibleSlides = new ArrayList<>();
  Map<String, Tags> repartitionTags = new HashMap<>();
  public Transitions transitions= new Transitions();


  public void calculate() throws Exception {

    allPossiblesSlides();


    Map<String, List<String>> collect = allPossibleSlides.stream().flatMap(s -> s.tags.stream())
            .collect(Collectors.groupingBy(Function.identity()));
    List<List<String>> collect1 = collect.values().stream().filter(v -> v.size() > 1).collect(Collectors.toList());
    System.out.println(collect);

    List<Slide> taken = new ArrayList<>();
    for (List<String> strings : collect1) {
    }


    Map<Integer, List<Slide>> tagSize = allPossibleSlides.stream().collect(Collectors.groupingBy(s -> s.tags.size()));

    for (Slide s1 : tagSize.get(33)) {
      for (Slide s2 : tagSize.get(33)) {
        if (s1 == s2) {
          continue;
        }
        if(Sets.intersection(s1.getTags(), s2.getTags()).size() >= 4) {
          System.out.println("found");
        }
      }

    }


    //List<Calcul> calculs = Collections.synchronizedList(new ArrayList<>());

    Path outputFolder = Paths.get("target/output-calculs");
    Files.createDirectories(outputFolder);

    String outputFileName = outputFolder.resolve(filename + ".calc").toString();
    PrintWriter fileResult = new PrintWriter(new FileWriter(outputFileName));
/*
    IntStream.range(0, allPossibleSlides.size()).parallel()
    .forEach(i -> {
      IntStream.range(i + 1, allPossibleSlides.size()).parallel()
          .forEach(j -> {
            Slide s1 = allPossibleSlides.get(i);
            Slide s2 = allPossibleSlides.get(j);
            int common_tags = Sets.intersection(s1.getTags(), s2.getTags()).size();
            int inS1Only = s1.getTags().size() - common_tags;
            int inS2Only = s2.getTags().size() - common_tags;
            Calcul calcul = new Calcul(i, s1, j, s2, common_tags, inS1Only, inS2Only, Math.min(Math.min(common_tags, inS1Only), inS2Only));
            //calculs.add(calcul);
            fileResult.println(calcul.s1id + " " + calcul.s2id + " " + calcul.common + " " + calcul.inS1Only + " " + calcul.inS2Only + " " + calcul.factor);
            System.out.println("calculing " + i + " " + j);
          });
    });*/

    fileResult.close();
    //System.out.println("Calculs " + calculs.size());
  }

  private void allPossiblesSlides() throws IOException {
    horizontalPhotos.addAll(mapPhoto.values().stream().filter(p -> p.horizontal).collect(Collectors.toList()));
    verticalPhotos.addAll(mapPhoto.values().stream().filter(p -> !p.horizontal).collect(Collectors.toList()));

    allPossibleSlides.addAll(horizontalPhotos.stream().map(Slide::new).collect(Collectors.toList()));
    for (int i = 0; i < verticalPhotos.size(); i++) {
      for (int j = i + 1; j < verticalPhotos.size(); j++) {
        allPossibleSlides.add(new Slide(verticalPhotos.get(i), verticalPhotos.get(j)));
      }
    }
    System.out.println("All possible slides " + allPossibleSlides.size());

    Path outputFolder = Paths.get("target/output-slides");
    Files.createDirectories(outputFolder);

    String outputFileName = outputFolder.resolve(filename + ".slides").toString();
    PrintWriter fileResult = new PrintWriter(new FileWriter(outputFileName));
    for (int i = 0; i < allPossibleSlides.size(); i++) {
      Slide slide = allPossibleSlides.get(i);
      fileResult.println(i + " " + slide.photo1.idPhoto + " " + (slide.photo2 != null ? slide.photo2.idPhoto : ""));
    }
    fileResult.close();
  }

  public int simulate() throws IOException {
    int score = 0;

    //System.out.println("Number of tags :" + repartitionTags.size());
    //repartitionTags.values().stream().sorted().forEach(tag -> System.out.println("Tag : " + tag.name + ", iteration " + tag.iteration));

    allPossiblesSlides();

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
      return new Calcul(0, s1, 0, s2, 0, 0, 0, interest_factor);
    }).max(Comparator.comparingInt(c -> c.factor)).get();
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
    return 0;
  }
}


