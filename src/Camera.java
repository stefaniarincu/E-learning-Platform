import java.util.List;

public class Camera {
    private String tipCamera;
    private List<String> utilitati;
    public Camera(String tip, List<String> utilitati) {
        this.tipCamera = tip;
        this.utilitati = utilitati;
    }

    public void setTipCamera(String tip) {
        this.tipCamera = tip;
    }

    public String getTipCamera() {
        return this.tipCamera;
    }

    public void setUtilitati(List<String> utilitati) {
        this.utilitati = utilitati;
    }

    public List<String> getUtilitati() {
        return this.utilitati;
    }

    @Override
    public String toString() {
        String utilitati = "";
        for(String s: getUtilitati()) {
            utilitati = utilitati + " -- " + s;
        }

        return " //  Camera: " + getTipCamera() + " ----> " + getUtilitati();
    }
}
