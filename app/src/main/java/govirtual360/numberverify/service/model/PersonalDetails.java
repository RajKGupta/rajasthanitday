package govirtual360.numberverify.service.model;


public class PersonalDetails {
    private String name,phone,district,state,UID,landArea,FCMToken,cropChosen;

    public String getCropChosen() {
        return cropChosen;
    }

    public void setCropChosen(String cropChosen) {
        this.cropChosen = cropChosen;
    }

    public String getUID() {
        return UID;
    }

    public String getFCMToken() {
        return FCMToken;
    }

    public void setFCMToken(String FCMToken) {
        this.FCMToken = FCMToken;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getLandArea() {
        return landArea;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    public PersonalDetails(String name, String phone, String district, String state, String UID, String landArea, String FCMToken) {
        this.name = name;
        this.phone = phone;
        this.district = district;
        this.state = state;
        this.UID = UID;
        this.landArea = landArea;
        this.FCMToken = FCMToken;
    }
    public PersonalDetails() {
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    }
