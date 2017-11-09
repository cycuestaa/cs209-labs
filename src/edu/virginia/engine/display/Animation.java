package edu.virginia.engine.display;

public class Animation {
    public String id;
    public int startFrame;
    public int endFrame;

    public Animation(String id, int startFrame, int endFrame){
        this.id=id;
        this.startFrame=startFrame;
        this.endFrame=endFrame;
    }
    public void setid(String id){
        this.id=id;
    }
    public String getid(){ return id;}

    public void setstartFrame(int startFrame){
        this.startFrame=startFrame;
    }
    public int getstartFrame(){return startFrame;}

    public void setendFrame(int endFrame){
        this.endFrame=endFrame;
    }
    public int getendFrame(){return endFrame;}
}
