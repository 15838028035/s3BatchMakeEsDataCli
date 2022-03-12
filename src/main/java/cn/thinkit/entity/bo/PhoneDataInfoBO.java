package cn.thinkit.entity.bo;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * 业务对接 BusinessBO 服务于前端查询
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneDataInfoBO implements Cloneable {

    private String serialNumber;
	
    private String insertTime;
	
    private String callStartTime;
	
    private String callEndTime;
	
    private String callNumber;
	
    private String calledNumber;
	
    private String callDirection;
	
    //0、合路、1、分路
    private Integer isEachRecord;
	
    //0表示摘机，1挂机。
    private Integer lineType;
	
    //目前没有实质用处
    private String channelNumber;
	
    //表示该条记录对应语音的信道来源。1表示PCM，2表示VoIP，3表示两线，4表示空中信号
    private Integer signalType;
	
    //一般由业务接口给出,如果是摘机记录，此字段必须为0,通话时长
    private Double holdDuration;
	
    //话单0、不重要、1、一般重要、2、非常重要
    private Integer isImportance = 0;
	
    //话单-1,未处理、0、部分处理、1、已处理
    private Integer handleState = -1;

    private String reserve1;
	
    private String reserve2;
	
    private String reserve3;
	
    private String reserve4;
	
	private String reserve5;
	
    private Long reserve6;
	
    private Long reserve7;
	
    private Double reserve8;
	
    private Double reserve9;
	
    private String reserve10;
	
    private String reserve11;
	
    private List<AudioList> audioList;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCalledNumber() {
        return calledNumber;
    }

    public void setCalledNumber(String calledNumber) {
        this.calledNumber = calledNumber;
    }

    public String getCallDirection() {
        return callDirection;
    }

    public void setCallDirection(String callDirection) {
        this.callDirection = callDirection;
    }

    public Integer getIsEachRecord() {
        return isEachRecord;
    }

    public void setIsEachRecord(Integer isEachRecord) {
        this.isEachRecord = isEachRecord;
    }

    public Integer getLineType() {
        return lineType;
    }

    public void setLineType(Integer lineType) {
        this.lineType = lineType;
    }

    public String getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(String channelNumber) {
        this.channelNumber = channelNumber;
    }

    public Integer getSignalType() {
        return signalType;
    }

    public void setSignalType(Integer signalType) {
        this.signalType = signalType;
    }

    public Double getHoldDuration() {
        return holdDuration;
    }

    public void setHoldDuration(Double holdDuration) {
        this.holdDuration = holdDuration;
    }

    public Integer getIsImportance() {
        return isImportance;
    }

    public void setIsImportance(Integer isImportance) {
        this.isImportance = isImportance;
    }

    public Integer getHandleState() {
        return handleState;
    }

    public void setHandleState(Integer handleState) {
        this.handleState = handleState;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(String callStartTime) {
        this.callStartTime = callStartTime;
    }

    public String getCallEndTime() {
        return callEndTime;
    }

    public void setCallEndTime(String callEndTime) {
        this.callEndTime = callEndTime;
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

    public Long getReserve6() {
        return reserve6;
    }

    public void setReserve6(Long reserve6) {
        this.reserve6 = reserve6;
    }

    public Long getReserve7() {
        return reserve7;
    }

    public void setReserve7(Long reserve7) {
        this.reserve7 = reserve7;
    }

    public Double getReserve8() {
        return reserve8;
    }

    public void setReserve8(Double reserve8) {
        this.reserve8 = reserve8;
    }

    public Double getReserve9() {
        return reserve9;
    }

    public void setReserve9(Double reserve9) {
        this.reserve9 = reserve9;
    }

    public String getReserve10() {
        return reserve10;
    }

    public void setReserve10(String reserve10) {
        this.reserve10 = reserve10;
    }

    public String getReserve11() {
        return reserve11;
    }

    public void setReserve11(String reserve11) {
        this.reserve11 = reserve11;
    }

    public List<AudioList> getAudioList() {
        return audioList;
    }

    public void setAudioList(List<AudioList> audioList) {
        this.audioList = audioList;
    }
	

	@Override
    public String toString() {
        return "PhoneDataInfoBO{" +
                "serialNumber='" + serialNumber + '\'' +
                ", insertTime='" + insertTime + '\'' +
                ", callStartTime='" + callStartTime + '\'' +
                ", callEndTime='" + callEndTime + '\'' +
                ", callNumber='" + callNumber + '\'' +
                ", calledNumber='" + calledNumber + '\'' +
                ", callDirection='" + callDirection + '\'' +
                ", isEachRecord=" + isEachRecord +
                ", lineType=" + lineType +
                ", channelNumber='" + channelNumber + '\'' +
                ", signalType=" + signalType +
                ", holdDuration=" + holdDuration +
                ", isImportance=" + isImportance +
                ", handleState=" + handleState +
                ", audioList=" + audioList +
                '}';
    }
}
