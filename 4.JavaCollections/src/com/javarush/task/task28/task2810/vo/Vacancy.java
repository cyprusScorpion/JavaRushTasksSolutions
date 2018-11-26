package com.javarush.task.task28.task2810.vo;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Vacancy {
    private String title;
    private String salary;
    private String city;
    private String companyName;
    private String siteName;
    private String url;
    private Calendar calendar = Calendar.getInstance();

    public String getTitle() {
        return title;
    }

    public String getSalary() {
        return salary;
    }

    public String getCity() {
        return city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return calendar.getTime();
    }

    public void setDate(Date date) {
        calendar.setTime(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vacancy)) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(getTitle(), vacancy.getTitle()) &&
                Objects.equals(getSalary(), vacancy.getSalary()) &&
                Objects.equals(getCity(), vacancy.getCity()) &&
                Objects.equals(getCompanyName(), vacancy.getCompanyName()) &&
                Objects.equals(getSiteName(), vacancy.getSiteName()) &&
                Objects.equals(getUrl(), vacancy.getUrl());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getTitle(), getSalary(), getCity(), getCompanyName(), getSiteName(), getUrl());
    }
}
