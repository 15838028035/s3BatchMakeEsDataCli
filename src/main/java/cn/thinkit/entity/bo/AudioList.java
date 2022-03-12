package cn.thinkit.entity.bo;

import java.util.List;

/**
 * Created by root on 4/14/17.
 */
public class AudioList {
    /**
     * audioNumber : 0007_01
     * audioPath : 0007.pcm
     * audioLength : 5.0
     * engineList : [{"engineName":"ENG","engineStatus":1,"comfimeStartTime":"2017-3-19","comfimeEndTime":"2017-3-20","engineResult":[{"serialNumber":1,"result":"ººÓï","startTime":"null","duration":"null","confidence":0.98,"judgeState":-1}]},{"engineName":"KW","engineStatus":1,"comfimeStartTime":"2017-3-23","comfimeEndTime":"2017-3-23","engineResult":[{"serialNumber":1,"result":"ó¦Ð·1","startTime":"0.96988","duration":"2.00","confidence":0.98,"judgeState":-1},{"serialNumber":2,"result":"ó¦Ð·2","startTime":"2.96988","duration":"2.00","confidence":0.98,"judgeState":-1},{"serialNumber":3,"result":"ó¦Ð·3","startTime":"4.96988","duration":"2.00","confidence":0.98,"judgeState":-1},{"serialNumber":4,"result":"ó¦Ð·4","startTime":"6.96988","duration":"2.00","confidence":0.98,"judgeState":-1}]},{"engineName":"STT","engineStatus":1,"comfimeStartTime":"2017-3-23","comfimeEndTime":"2017-3-23","engineResult":[{"serialNumber":1,"result":"ÎÒÔÚ³Ôó¦Ð·1","startTime":"0.96988","duration":"2.00","confidence":0.98,"judgeState":-1},{"serialNumber":2,"result":"ÎÒÔÚ³Ôó¦Ð·2","startTime":"2.96988","duration":"2.00","confidence":0.98,"judgeState":-1},{"serialNumber":3,"result":"ÎÒÔÚ³Ôó¦Ð·3","startTime":"4.96988","duration":"2.00","confidence":0.98,"judgeState":-1},{"serialNumber":4,"result":"ÎÒÔÚ³Ôó¦Ð·4","startTime":"6.96988","duration":"2.00","confidence":0.98,"judgeState":-1}]},{"engineName":"SPK","engineStatus":1,"comfimeStartTime":"2017-3-23","comfimeEndTime":"2017-3-23","engineResult":[{"serialNumber":1,"result":"0001","startTime":"null","duration":"null","confidence":0.99,"judgeState":-1}]}]
     */

    private String audioNumber;
    private String audioPath;
    private Double audioLength;
    private String reserve1;
    private String reserve2;
    private String reserve3;
    private String reserve4;
    private String reserve5;
    private String reserve6 = "1900-01-01 00:00:00";
    private List<EngineList> engineList;

    public String getAudioNumber() {
        return audioNumber;
    }

    public void setAudioNumber(String audioNumber) {
        this.audioNumber = audioNumber;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public Double getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(Double audioLength) {
        this.audioLength = audioLength;
    }

    public List<EngineList> getEngineList() {
        return engineList;
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

    public String getReserve6() {
        return reserve6;
    }

    public void setReserve6(String reserve6) {
        this.reserve6 = reserve6;
    }

    public void setEngineList(List<EngineList> engineList) {
        this.engineList = engineList;
    }
}
