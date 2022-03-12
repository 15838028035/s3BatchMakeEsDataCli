package cn.thinkit.entity.bo;


import java.util.List;

import cn.thinkit.util.DateFormatUtil;

/**
 * Created by root on 4/14/17.
 */
public class EngineList {

    /**
     * engineName : ENG
     * engineStatus : 1
     * comfimeStartTime : 2017-3-19
     * comfimeEndTime : 2017-3-20
     * engineResult : [{"serialNumber":1,"result":"ººÓï","startTime":"null","duration":"null","confidence":0.98,"judgeState":-1}]
     */

    private String engineName;
    private Integer engineStatus = -1;
    private String confirmTime = DateFormatUtil.getData();
    private String confirmEndTime = DateFormatUtil.getData();
    private String reserve1;
    private String reserve2;
    private String reserve3;
    private String reserve4;
    private String reserve5 = "1900-01-01 00:00:00";
    private List<EngineResult> engineResult;

    public String getEngineName() {
        return engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public Integer getEngineStatus() {
        return engineStatus;
    }

    public void setEngineStatus(Integer engineStatus) {
        this.engineStatus = engineStatus;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmEndTime() {
        return confirmEndTime;
    }

    public void setConfirmEndTime(String confirmEndTime) {
        this.confirmEndTime = confirmEndTime;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }

    public String getReserve4() {
        return reserve4;
    }

    public void setReserve4(String reserve4) {
        this.reserve4 = reserve4;
    }

    public String getReserve5() {
        return reserve5;
    }

    public void setReserve5(String reserve5) {
        this.reserve5 = reserve5;
    }

    public List<EngineResult> getEngineResult() {
        return engineResult;
    }

    public void setEngineResult(List<EngineResult> engineResult) {
        this.engineResult = engineResult;
    }
}
