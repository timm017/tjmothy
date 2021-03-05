package com.tjmothy.stats;

/**
 * @author tmckeown
 */
public class Sport
{
    public final static int GIRLS_BASKETBALL = 1;
    public final static int GIRLS_CROSS_COUNTRY = 2;
    public final static int BOYS_CROSS_COUNTRY = 3;
    public final static int FIELD_HOCKEY = 4;
    public final static int FOOTBALL = 5;
    public final static int GOLF = 6;
    public final static int GIRLS_SOCCER = 7;
    public final static int BOYS_SOCCER = 8;
    public final static int TENNIS = 9;
    public final static int GIRLS_VOLLEYBALL = 10;
    public final static int SWIMMING_DIVING = 11;
    public final static int WRESTLING = 12;
    public final static int BASEBALL = 13;
    public final static int SOFTBALL = 14;
    public final static int GIRLS_LOCROSSE = 15;
    public final static int BOYS_LOCROSSE = 16;
    public final static int GIRLS_TRACK = 17;
    public final static int BOYS_TRACK = 18;
    public final static int BOYS_BASKETBALL = 19;
    public final static int BOYS_VOLLEYBALL = 20;

    private int sport = 0;

    public Sport(int sport)
    {
        this.sport = sport;
    }

    /**
     * gets the correct procedure for the correlating sport to run when tabulator clicks on the "update" button.
     *
     * @return
     */
    public static String getProcedure(int sport)
    {
        switch (sport)
        {
            case GIRLS_BASKETBALL:
                return "girlsBasketball_rating()";
            case GIRLS_CROSS_COUNTRY:
                return "girlsCrossCountry_rating()";
            case BOYS_CROSS_COUNTRY:
                return "boysCrossCountry()";
            case FIELD_HOCKEY:
                return "fieldHockey_rating()";
            case FOOTBALL:
                return "football_rating()";
            case GOLF:
                return "golf_rating()";
            case GIRLS_SOCCER:
                return "girlsSoccer_rating()";
            case BOYS_SOCCER:
                return "boysSoccer_rating()";
            case TENNIS:
                return "tennis_rating()";
            case GIRLS_VOLLEYBALL:
                return "girlsVolleyball_rating()";
            case SWIMMING_DIVING:
                return "swimmingDiving_rating()";
            case WRESTLING:
                return "wrestling_rating()";
            case BASEBALL:
                return "baseball_rating()";
            case SOFTBALL:
                return "softball_rating()";
            case GIRLS_LOCROSSE:
                return "girlsLacrosse_rating()";
            case BOYS_LOCROSSE:
                return "boysLacrosse_rating()";
            case GIRLS_TRACK:
                return "girlsTrack_rating()";
            case BOYS_TRACK:
                return "boysTrack_rating()";
            case BOYS_BASKETBALL:
                return "boysBasketball_rating()";
            case BOYS_VOLLEYBALL:
                return "boysVolleyball_rating()";
            default:
                return "baseball_rating()";
        }

    }

}
