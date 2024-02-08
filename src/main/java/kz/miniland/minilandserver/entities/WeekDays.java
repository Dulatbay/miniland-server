package kz.miniland.minilandserver.entities;


public enum WeekDays {
    SUN, MON, TUE, WED, THU, FRI, SAT;

    public static WeekDays getByInteger(Integer number) {
        return switch (number) {
            case 1 -> SUN;
            case 2 -> MON;
            case 3 -> TUE;
            case 4 -> WED;
            case 5 -> THU;
            case 6 -> FRI;
            case 7 -> SAT;
            default -> throw new IllegalArgumentException("Days must be in range 1-7");
        };
    }

    public Integer getInteger() {
        return switch (this) {
            case SUN -> 1;
            case MON -> 2;
            case TUE -> 3;
            case WED -> 4;
            case THU -> 5;
            case FRI -> 6;
            case SAT -> 7;
        };
    }
}
