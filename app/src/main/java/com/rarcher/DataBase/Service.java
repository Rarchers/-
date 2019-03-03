package com.rarcher.DataBase;

/**
 * Created by 25532 on 2019/3/3.
 */

public class Service {
    String title,start_time,continue_time,location,special_request,context,codeID,service_name,service_sex,service_phones,service_code;
    int service_age;
    int statues;

    public String getTitle() {
        return title;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getContinue_time() {
        return continue_time;
    }

    public String getLocation() {
        return location;
    }

    public String getSpecial_request() {
        return special_request;
    }

    public String getContext() {
        return context;
    }

    public String getCodeID() {
        return codeID;
    }

    public String getService_name() {
        return service_name;
    }

    public String getService_sex() {
        return service_sex;
    }

    public String getService_phones() {
        return service_phones;
    }

    public String getService_code() {
        return service_code;
    }

    public int getService_age() {
        return service_age;
    }

    public int getStatues() {
        return statues;
    }

    public Service(String title, String start_time, String continue_time, String location, String special_request, String context, String codeID, String service_name, String service_sex, String service_phones, String service_code, int service_age, int statues) {

        this.title = title;
        this.start_time = start_time;
        this.continue_time = continue_time;
        this.location = location;
        this.special_request = special_request;
        this.context = context;
        this.codeID = codeID;
        this.service_name = service_name;
        this.service_sex = service_sex;
        this.service_phones = service_phones;
        this.service_code = service_code;
        this.service_age = service_age;
        this.statues = statues;
    }
}
