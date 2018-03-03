package fun.google.hash_code_2018.model;

public interface Ride {

    String getRideId();

    Point getStart();

    Point getFinish();

    int getEarliestStart();

    int getLatestFinish();

    int getDuration();

    int getScore();

    int getSize();

}
