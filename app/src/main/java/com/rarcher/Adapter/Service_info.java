package com.rarcher.Adapter;

/**
 * Created by 25532 on 2019/2/14.
 */

public class Service_info {
    String name,data,start_time,end_time,location,special_request,context,codeID,service_name,service_sex,service_code,service_phones;
    int service_age;
    public Service_info(String name, String data, String start_time, String end_time, String location, String special_request, String context, String codeID, String service_name, String service_sex, String service_code, String service_phones, int service_age) {
        this.name = name;
        this.data = data;
        this.start_time = start_time;
        this.end_time = end_time;
        this.location = location;
        this.special_request = special_request;
        this.context = context;
        this.codeID = codeID;
        this.service_name = service_name;
        this.service_sex = service_sex;
        this.service_code = service_code;
        this.service_phones = service_phones;
        this.service_age = service_age;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
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

    public String getService_code() {
        return service_code;
    }

    public String getService_phones() {
        return service_phones;
    }

    public int getService_age() {
        return service_age;
    }
}
