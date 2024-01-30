package kz.miniland.minilandserver.entities;


public enum WeekDays {
    SUN, MON, TUE, WED, THU, FRI, SAT;

    public static WeekDays getByInteger(Integer number) {
        return switch (number) {
            case 0 -> SUN;
            case 1 -> MON;
            case 2 -> TUE;
            case 3 -> WED;
            case 4 -> THU;
            case 5 -> FRI;
            case 6 -> SAT;
            default -> throw new IllegalArgumentException("Invalid argument");
        };
    }
}
