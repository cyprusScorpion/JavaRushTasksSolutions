package com.javarush.task.task39.task3913;


import com.javarush.task.task39.task3913.query.*;

import java.io.*;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {

  private Path logDir;

  public LogParser(Path logDir) {
    this.logDir = logDir;
  }

  @Override
  public int getNumberOfUniqueIPs(Date after, Date before) {
    return getUniqueIPs(after, before).size();
  }

  @Override
  public Set<String> getUniqueIPs(Date after, Date before) {
    Set<String> IPs = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      IPs.add(element.ip);
    }
    return IPs;
  }

  @Override
  public Set<String> getIPsForUser(String user, Date after, Date before) {
    Set<String> IPs = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.user.equals(user))
        IPs.add(element.ip);
    }
    return IPs;
  }

  @Override
  public Set<String> getIPsForEvent(Event event, Date after, Date before) {
    Set<String> IPs = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.event.equals(event))
        IPs.add(element.ip);
    }
    return IPs;
  }

  @Override
  public Set<String> getIPsForStatus(Status status, Date after, Date before) {
    Set<String> IPs = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.status.equals(status))
        IPs.add(element.ip);
    }
    return IPs;
  }

  @Override
  public Set<String> getAllUsers() {
    Set<String> users = new HashSet<>();
    for (LogElement element : getLogElements(logDir)) {
      users.add(element.user);
    }
    return users;
  }

  @Override
  public int getNumberOfUsers(Date after, Date before) {
    Set<String> users = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      users.add(element.user);
    }
    return users.size();
  }

  @Override
  public int getNumberOfUserEvents(String user, Date after, Date before) {
    Set<Event> events = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.user.equalsIgnoreCase(user))
        events.add(element.event);
    }
    return events.size();
  }

  @Override
  public Set<String> getUsersForIP(String ip, Date after, Date before) {
    Set<String> users = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (ip.equalsIgnoreCase(element.ip))
        users.add(element.user);
    }
    return users;
  }

  @Override
  public Set<String> getLoggedUsers(Date after, Date before) {
    Set<String> users = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.event.equals(Event.LOGIN))
        users.add(element.user);
    }
    return users;
  }

  @Override
  public Set<String> getDownloadedPluginUsers(Date after, Date before) {
    Set<String> users = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.event.equals(Event.DOWNLOAD_PLUGIN) && element.status.equals(Status.OK))
        users.add(element.user);
    }
    return users;
  }

  @Override
  public Set<String> getWroteMessageUsers(Date after, Date before) {
    Set<String> users = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.event.equals(Event.WRITE_MESSAGE) && element.status.equals(Status.OK))
        users.add(element.user);
    }
    return users;
  }

  @Override
  public Set<String> getSolvedTaskUsers(Date after, Date before) {
    Set<String> users = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.event.equals(Event.SOLVE_TASK))
        users.add(element.user);
    }
    return users;
  }

  @Override
  public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
    Set<String> users = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.event.equals(Event.SOLVE_TASK) && element.task == task)
        users.add(element.user);
    }
    return users;
  }

  @Override
  public Set<String> getDoneTaskUsers(Date after, Date before) {
    Set<String> users = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.event.equals(Event.DONE_TASK))
        users.add(element.user);
    }
    return users;
  }

  @Override
  public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
    Set<String> users = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.event.equals(Event.DONE_TASK) && element.task == task)
        users.add(element.user);
    }
    return users;
  }

  @Override
  public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
    Set<Date> dates = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.user.equalsIgnoreCase(user) && element.event.equals(event))
        dates.add(element.date);
    }
    return dates;
  }

  @Override
  public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
    Set<Date> dates = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.status.equals(Status.FAILED))
        dates.add(element.date);
    }
    return dates;
  }

  @Override
  public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
    Set<Date> dates = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.status.equals(Status.ERROR))
        dates.add(element.date);
    }
    return dates;
  }

  @Override
  public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
    Date date = null;
    List<LogElement> elements = listLogsBetweenDates(after, before);
    Collections.sort(elements, (o1, o2) -> o1.date.compareTo(o2.date));
    for (LogElement element : elements) {
      if (element.user.equalsIgnoreCase(user) && element.event.equals(Event.LOGIN) && element.status.equals(Status.OK)) {
        date = element.date;
        break;
      }
    }
    return date;
  }

  @Override
  public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
    Date date = null;
    List<LogElement> elements = listLogsBetweenDates(after, before);
    Collections.sort(elements, (o1, o2) -> o1.date.compareTo(o2.date));
    for (LogElement element : elements) {
      if (element.user.equalsIgnoreCase(user) && element.event.equals(Event.SOLVE_TASK) && element.task == task) {
        date = element.date;
        break;
      }
    }
    return date;
  }

  @Override
  public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
    Date date = null;
    List<LogElement> elements = listLogsBetweenDates(after, before);
    Collections.sort(elements, (o1, o2) -> o1.date.compareTo(o2.date));
    for (LogElement element : elements) {
      if (element.user.equalsIgnoreCase(user) && element.event.equals(Event.DONE_TASK) && element.task == task) {
        date = element.date;
        break;
      }
    }
    return date;
  }

  @Override
  public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
    Set<Date> dates = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.user.equalsIgnoreCase(user) && element.event.equals(Event.WRITE_MESSAGE))
        dates.add(element.date);
    }
    return dates;
  }

  @Override
  public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
    Set<Date> dates = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.user.equalsIgnoreCase(user) && element.event.equals(Event.DOWNLOAD_PLUGIN))
        dates.add(element.date);
    }
    return dates;
  }

  @Override
  public int getNumberOfAllEvents(Date after, Date before) {
    return getAllEvents(after, before).size();
  }

  @Override
  public Set<Event> getAllEvents(Date after, Date before) {
    Set<Event> events = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      events.add(element.event);
    }
    return events;
  }

  @Override
  public Set<Event> getEventsForIP(String ip, Date after, Date before) {
    Set<Event> events = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.ip.equals(ip))
        events.add(element.event);
    }
    return events;
  }

  @Override
  public Set<Event> getEventsForUser(String user, Date after, Date before) {
    Set<Event> events = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.user.equalsIgnoreCase(user))
        events.add(element.event);
    }
    return events;
  }

  @Override
  public Set<Event> getFailedEvents(Date after, Date before) {
    Set<Event> events = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.status.equals(Status.FAILED))
        events.add(element.event);
    }
    return events;
  }

  @Override
  public Set<Event> getErrorEvents(Date after, Date before) {
    Set<Event> events = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.status.equals(Status.ERROR))
        events.add(element.event);
    }
    return events;
  }

  @Override
  public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
    int attemptCounter = 0;
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.task == task && element.event.equals(Event.SOLVE_TASK))
        attemptCounter++;
    }
    return attemptCounter;
  }

  @Override
  public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
    int success = 0;
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (task == element.task && Event.DONE_TASK.equals(element.event))
        success++;
    }
    return success;
  }

  @Override
  public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
    Map<Integer, Integer> map = new HashMap<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.event.equals(Event.SOLVE_TASK))
        map.merge(element.task, 1, (i1, i2) -> i1 + i2);
    }
    return map;
  }

  @Override
  public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
    Map<Integer, Integer> map = new HashMap<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      if (element.event.equals(Event.DONE_TASK))
        map.merge(element.task, 1, (i1, i2) -> i1 + i2);
    }
    return map;
  }

  @Override
  public Set<Object> execute(String query) {
    Set result = new HashSet<>();
    if (query.split(" ").length < 3){
      switch (query) {
        case "get ip" :
          result = getUniqueIPs(null, null);
          break;
        case "get user" :
          result = getAllUsers();
          break;
        case "get date" :
          result = getAllDates(null, null);
          break;
        case "get event" :
          result = getAllEvents(null, null);
          break;
        case "get status" :
          result = getAllStatuses(null, null);
          break;
      }
    }
    else if (query.split(" ").length > 2 && !query.contains("and date between"))
      result = executeWithParam(query);
    else result = executeBetweenDates(query);
    return result;
  }


  private Set<Object> executeWithParam(String query) {
    List<LogElement> elements = getLogElements(logDir);
    String[] queryParams = query.split(" ");
    String field1 = queryParams[1];
    String field2 = queryParams[3];
    String value = query.split("=")[1].replaceAll("\\\"", "").trim();
    Set set = null;
    if (field1.equalsIgnoreCase("ip"))
      set = getIPsForParam(field2, value, elements);
    if (field1.equalsIgnoreCase("user"))
      set = getUsersForParam(field2, value, elements);
    if (field1.equalsIgnoreCase("date"))
      set = getDatesForParam(field2, value, elements);
    if (field1.equalsIgnoreCase("event"))
      set = getEventsForParam(field2, value, elements);
    if (field1.equalsIgnoreCase("status"))
      set = getStatusesForParam(field2, value, elements);
    return set;
  }

  private Set<Date> getDatesForParam(String param, String value, List<LogElement> elements) {
    Set result = new HashSet();
    for (LogElement e : elements) {
      switch (param) {
        case "user" :
          if (e.user.equalsIgnoreCase(value))
            result.add(e.date);
          break;
        case "ip" :
          if (e.ip.equals(value))
            result.add(e.date);
          break;
        case "event" :
          if (e.event.equals(Event.valueOf(value)))
            result.add(e.date);
          break;
        case "status" :
          if (e.status.equals(Status.valueOf(value)))
            result.add(e.date);
          break;
      }
    }
    return result;
  }

  private Set<Event> getEventsForParam(String param, String value, List<LogElement> elements) {
    Set result = new HashSet();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    for (LogElement e : elements) {
      switch (param) {
        case "user" :
          if (e.user.equalsIgnoreCase(value))
            result.add(e.event);
          break;
        case "date" :
          try {
            Date date = dateFormat.parse(value);
            if (e.date.equals(date))
              result.add(e.event);
          } catch (ParseException e1) {
            e1.printStackTrace();
          }
          break;
        case "ip" :
          if (e.ip.equals(value))
            result.add(e.event);
          break;
        case "status" :
          if (e.status.equals(Status.valueOf(value)))
            result.add(e.event);
          break;
      }
    }
    return result;
  }

  private Set<Status> getStatusesForParam(String param, String value, List<LogElement> elements) {
    Set result = new HashSet();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    for (LogElement e : elements) {
      switch (param) {
        case "user" :
          if (e.user.equalsIgnoreCase(value))
            result.add(e.status);
          break;
        case "date" :
          try {
            Date date = dateFormat.parse(value);
            if (e.date.equals(date))
              result.add(e.status);
          } catch (ParseException e1) {
            e1.printStackTrace();
          }
          break;
        case "event" :
          if (e.event.equals(Event.valueOf(value)))
            result.add(e.status);
          break;
        case "ip" :
          if (e.ip.equals(value))
            result.add(e.status);
          break;
      }
    }
    return result;
  }

  private Set<String> getIPsForParam(String param, String value, List<LogElement> elements) {
    Set result = new HashSet();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    for (LogElement e : elements) {
      switch (param) {
        case "user" :
          if (e.user.equals(value))
            result.add(e.ip);
          break;
        case "date" :
          try{
            Date date = dateFormat.parse(value);
            if (e.date.getTime() == date.getTime())
              result.add(e.ip);
          }
          catch (ParseException ex) {}
          break;
        case "event" :
          if (e.event.equals(Event.valueOf(value)))
            result.add(e.ip);
          break;
        case "status" :
          if (e.status.equals(Status.valueOf(value)))
            result.add(e.ip);
          break;
      }
    }
    return result;
  }

  private Set<String> getUsersForParam(String param, String value, List<LogElement> elements) {
    Set result = new HashSet();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    for (LogElement e : elements) {
      switch (param) {
        case "ip" :
          if (e.ip.equalsIgnoreCase(value))
            result.add(e.user);
          break;
        case "date" :
          try {
            Date date = dateFormat.parse(value);
            if (e.date.equals(date))
              result.add(e.user);
          } catch (ParseException e1) {
            e1.printStackTrace();
          }
          break;
        case "event" :
          if (e.event.equals(Event.valueOf(value)))
            result.add(e.user);
          break;
        case "status" :
          if (e.status.equals(Status.valueOf(value)))
            result.add(e.user);
          break;
      }
    }
    return result;
  }

  private Set<Date> getAllDates(Date after, Date before) {
    Set<Date> dates = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      dates.add(element.date);
    }
    return dates;
  }

  private Set<Status> getAllStatuses(Date after, Date before) {
    Set<Status> statuses = new HashSet<>();
    for (LogElement element : listLogsBetweenDates(after, before)) {
      statuses.add(element.status);
    }
    return statuses;
  }

  private List<LogElement> listLogsBetweenDates(Date after, Date before) {
    List<LogElement> result = new ArrayList<>();
    for (LogElement element : getLogElements(logDir)) {
      if (after != null && before != null) {
        if (element.date.getTime() > after.getTime() && element.date.getTime() < before.getTime())
          result.add(element);
      }
      else if (after == null && before != null) {
        if (element.date.getTime() < before.getTime())
          result.add(element);
      }
      else if (after != null && before == null) {
        if (element.date.getTime() > after.getTime())
          result.add(element);
      }
      else result.add(element);
    }
    return result;
  }

  private List<LogElement> getLogElements(Path path) {
    List<LogElement> elements = new ArrayList<>();
    File[] files = path.toFile().listFiles();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    for (File f : files) {
      if (!f.getName().endsWith(".log"))
        continue;
      try (BufferedReader reader = new BufferedReader(new FileReader(f.getAbsoluteFile()))) {
        while (reader.ready()) {
          String[] parts = reader.readLine().split("\t");
          String eventName = parts[3].split(" ")[0];
          int task = parts[3].split(" ").length > 1 ? Integer.parseInt(parts[3].split(" ")[1]) : 0;
          elements.add(new LogElement(parts[0],
                  parts[1],
                  dateFormat.parse(parts[2]),
                  Event.valueOf(eventName),
                  Status.valueOf(parts[4]),
                  task));
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return elements;
  }

  private class LogElement {
    private String ip;
    private String user;
    private Date date;
    private Event event;
    private Status status;
    private int task;

    public LogElement(String ip, String user, Date date, Event event, Status status, int task) {
      this.ip = ip;
      this.user = user;
      this.date = date;
      this.event = event;
      this.status = status;
      this.task = task;
    }
  }


  private Set<Object> executeBetweenDates(String query) {
    Set result = new HashSet();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    String[] queryParams = query.split(" = ");
    queryParams[1] = queryParams[1].replaceAll("date between ", "");
    String[] values = queryParams[1].split(" and ");
    String value = values[0].replaceAll("\\\"", "").trim();
    String param = queryParams[0].split(" ")[3].trim();
    Date after = null;
    Date before = null;
    try {
      after = dateFormat.parse(values[1].replaceAll("\\\"", ""));
      before = dateFormat.parse(values[2].replaceAll("\\\"", ""));
    }
    catch (ParseException e) {}
    List<LogElement> elements = listLogsBetweenDates(after, before);
    if (query.toLowerCase().startsWith("get ip")) {
      result = getIPsForParam(param, value, elements);
    }
    else if (query.toLowerCase().startsWith("get user")) {
      result = getUsersForParam(param, value, elements);
    }
    else if (query.toLowerCase().startsWith("get date")) {
      result = getDatesForParam(param, value, elements);
    }
    else if (query.toLowerCase().startsWith("get event")) {
      result = getEventsForParam(param, value, elements);
    }
    else if (query.toLowerCase().startsWith("get status")) {
      result = getStatusesForParam(param, value, elements);
    }
    return result;
  }
}