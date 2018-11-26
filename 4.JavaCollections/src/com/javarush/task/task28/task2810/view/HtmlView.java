package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
    private final String filePath = "./4.JavaCollections/src/" +
            this.getClass().getPackage().getName().replace('.', '/') + "/vacancies.html";

    private Controller controller;

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            updateFile(getUpdatedFileContent(vacancies));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {

        String content = "";
        try {
            Document doc = getDocument();
            Element el = doc.getElementsByClass("template").get(0);

            Element templateEl = el.clone();
            templateEl.removeAttr("style");
            templateEl.removeClass("template");

            for (Element vacEl : doc.select("tr[class=vacancy]"))
                vacEl.remove();

            for (Vacancy vac : vacancies) {
                Element copyTemplateEl = templateEl.clone();
                copyTemplateEl.getElementsByClass("city").get(0).text(vac.getCity());
                copyTemplateEl.getElementsByClass("companyName").get(0).text(vac.getCompanyName());
                copyTemplateEl.getElementsByClass("salary").get(0).text(vac.getSalary());

                Element elementLink = copyTemplateEl.getElementsByClass("title").first();
                elementLink.text(vac.getTitle());
                elementLink.attr("href", vac.getUrl());

                //copyTemplateEl.getElementsByTag("a").get(0).text(vac.getTitle());
                el.before(copyTemplateEl.outerHtml());
            }
            content = doc.outerHtml();
        } catch (Exception e) {
            e.printStackTrace();
            return "Some exception occurred";
        }
        return content;
    }

    private void updateFile(String doc) {
        File file = new File(filePath);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(doc);
            fw.close();
        } catch (IOException ignored) {

        }
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }
}
