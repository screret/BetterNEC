package me.screret.betternec.objects;

public class AverageItem {
    public String id;
    public int demand;
    public int ahAvgPrice;

    public AverageItem(String id, int demand, int ahAvgPrice) {
        this.id = id;
        this.demand = demand;
        this.ahAvgPrice = ahAvgPrice;
    }

    public String toString(){
        return id + ";" + demand + ";" + ahAvgPrice;
    }
}
