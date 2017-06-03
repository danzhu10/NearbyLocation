package android.tes.mangjek.model;

import java.io.Serializable;

/**
 * Created by EduSPOT on 03/06/2017.
 */

public class PlaceDetails implements Serializable{
    Result result;

    public Result getResult() {
        return result;
    }

    public class Result {
        String formatted_phone_number;
        String website;
        String vicinity;
        String name;
        String adr_address;

        public String getFormatted_phone_number() {
            return formatted_phone_number;
        }

        public String getWebsite() {
            return website;
        }

        public String getVicinity() {
            return vicinity;
        }

        public String getName() {
            return name;
        }

        public String getAdr_address() {
            return adr_address;
        }
    }
}
