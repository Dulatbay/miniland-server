package kz.miniland.minilandserver.entities;

public interface OrderWithPriceAndTime {
    Double getTotalFullPrice();
    Long getTotalFullTime();
    String getAuthorName();
}
