package cn.thinkit.entity.bo;


/**
 * Created by root on 4/14/17.
 */
public class EngineResult {
    /**
     * serialNumber : 1
     * result : ººÓï
     * startTime : null
     * duration : null
     * confidence : 0.98
     * judgeState : -1
     */

    private Integer serialNumber;
    private String result;
    private String remark;
    private Double startTime;
    private Double duration;
    private Double confidence;
    private Integer judgeState = -1;
    private String reserve1;
    private String reserve2;
    private String reserve3;

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

 

    public Double getStartTime() {
		return startTime;
	}

	public void setStartTime(Double startTime) {
		this.startTime = startTime;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}

	public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Integer getJudgeState() {
        return judgeState;
    }

    public void setJudgeState(Integer judgeState) {
        this.judgeState = judgeState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
