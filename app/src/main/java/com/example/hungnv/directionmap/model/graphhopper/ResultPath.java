
package com.example.hungnv.directionmap.model.graphhopper;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultPath {

    @SerializedName("startPoint")
    @Expose
    private StartPoint startPoint;
    @SerializedName("endPoint")
    @Expose
    private EndPoint endPoint;
    @SerializedName("instructionList")
    @Expose
    private List<InstructionList> instructionList = null;

    public StartPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(StartPoint startPoint) {
        this.startPoint = startPoint;
    }

    public EndPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(EndPoint endPoint) {
        this.endPoint = endPoint;
    }

    public List<InstructionList> getInstructionList() {
        return instructionList;
    }

    public void setInstructionList(List<InstructionList> instructionList) {
        this.instructionList = instructionList;
    }

}
