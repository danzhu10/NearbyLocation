package android.tes.mangjek.model;

import java.io.Serializable;

/**
 * Created by EduSPOT on 03/06/2017.
 */

public class PlaceData implements Serializable{
    String placeName;
    String latitud;
    String longitude;
    String vicinity;
    String reference;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getLatitud() {
        return latitud;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getVicinity() {
        return vicinity;
    }
}
