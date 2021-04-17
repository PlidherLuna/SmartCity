package DateStats;

/** Classe per le statistiche di un periodo
 */

public class DateStats {
    private String startDate;
    private String endDate;
    private float avrgPollution;
    private float avrgTemp;
    private int avrgCnt;
    private float maxPollution;
    private float maxTemp;
    private int maxCnt;

    public DateStats(String startDate,String endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setAvrgPollution(float avrgPollution) {
        this.avrgPollution = avrgPollution;
    }

    public void setAvrgTemp(float avrgTemp) {
        this.avrgTemp = avrgTemp;
    }

    public void setAvrgCnt(int avrgCnt) {
        this.avrgCnt = avrgCnt;
    }

    public void setMaxPollution(float maxPollution) {
        if(maxPollution > this.maxPollution)
            this.maxPollution = maxPollution;
    }

    public void setMaxTemp(float maxTemp) {
        if(maxTemp > this.maxTemp)
            this.maxTemp = maxTemp;
    }

    public void setMaxCnt(int maxCnt) {
        if(maxCnt > this.maxCnt)
            this.maxCnt = maxCnt;
    }

    public float getAvrgPollution() {
        return avrgPollution;
    }

    public float getMaxPollution() {
        return maxPollution;
    }

    public float getAvrgTemp() {
        return avrgTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public int getAvrgCnt() {
        return avrgCnt;
    }

    public int getMaxCnt() {
        return maxCnt;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

}
