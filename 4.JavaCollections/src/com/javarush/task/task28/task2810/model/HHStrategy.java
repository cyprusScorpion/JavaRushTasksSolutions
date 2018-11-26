package com.javarush.task.task28.task2810.model;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=java+%s&page=%d";
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/66.0.3359.181 Chrome/66.0.";
    private static final String REFERRER = "https://hh.ua/";

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){
        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }
    };

    private static SimpleDateFormat format = new SimpleDateFormat("d\u00a0MMMM yyyy", myDateFormatSymbols);

    @Override
    public List<Vacancy> getVacancies(String searchString) {
        List<Vacancy> vacancies = new ArrayList<>();
        Document doc;
        Elements els;
        int page = 0;
        while (true) {
            try {
                doc = getDocument(searchString, page++);
                els = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (els.isEmpty())
                    break;
                for (Element el : els) {
                    Elements elSalary = el.select("[data-qa=vacancy-serp__vacancy-compensation]");
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(el.select("[data-qa=vacancy-serp__vacancy-title]").first().text());
                    vacancy.setSalary(elSalary.size() == 0 ? "" : elSalary.first().text());
                    vacancy.setCity(el.select("[data-qa=vacancy-serp__vacancy-address]").first().text());
                    vacancy.setCompanyName(el.select("[data-qa=vacancy-serp__vacancy-employer]").first().text());
                    vacancy.setSiteName(REFERRER);
                    vacancy.setUrl(el.select("[data-qa=vacancy-serp__vacancy-title]").first().attr("href"));
                    vacancy.setDate(format.parse(el.select("[data-qa=vacancy-serp__vacancy-date]").first().text() + " 2016"));
                    vacancies.add(vacancy);
                }
            }
            catch (IOException ignored) {
            }
            catch (ParseException parseDateError) {
                System.out.println(parseDateError.getCause());
            }
        }
        return vacancies;
    }

    protected Document getDocument(String searchString, int page) throws IOException {
        return Jsoup.connect(String.format(URL_FORMAT, searchString, page))
                .userAgent(USER_AGENT)
                .referrer(REFERRER)
                .get();
    }
}
