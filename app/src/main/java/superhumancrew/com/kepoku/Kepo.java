package superhumancrew.com.kepoku;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by dedie on 12/2/17.
 */
@IgnoreExtraProperties
public class Kepo implements Serializable {

   private String kepo;
    private String waktu;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKepo() {
        return kepo;
    }

    public void setKepo(String kepo) {
        this.kepo = kepo;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public Kepo(String kepo, String waktu) {
        this.kepo = kepo;
        this.waktu = waktu;
    }

    @Override
    public String toString() {
        return "Kepo{" +
                "kepo='" + kepo + '\'' +
                ", waktu='" + waktu + '\'' +
                '}';
    }

    public Kepo(String kepo, String waktu, String key) {
        this.kepo = kepo;
        this.waktu = waktu;
        this.key = key;
    }

    public Kepo(){

    }
}
