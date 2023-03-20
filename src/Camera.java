import java.util.List;

public class Camera {
    String tip_camera;
    List<String> utilitati;
    public Camera(String tip, List<String> utilitati){
        this.tip_camera = tip;
        this.utilitati = utilitati;
    }
    public void setTip_camera(String tip){
        this.tip_camera = tip;
    }
    public String getTip_camera(){
        return this.tip_camera;
    }
    public void setUtilitati(List<String> utilitati){
        this.utilitati = utilitati;
    }
    public List<String> getUtilitati(){
        return this.utilitati;
    }
    @Override
    public String toString() {
        String utilitati = "";
        for(String s: getUtilitati())
            utilitati = utilitati + " -- " + s;
        return " //  Camera: " + getTip_camera() + " ----> " + utilitati;
    }
}
