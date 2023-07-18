package io.cucumber.shouty;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Network {

  public static final Pattern BUY_PATTERN = Pattern.compile("buy", Pattern.CASE_INSENSITIVE);
  private final List<Person> listeners = new ArrayList<>();
  private final int range;

  public Network(int range) {
    this.range = range;
  }

  public void subscribe(Person person) {
    listeners.add(person);
  }
  public void broadcast(String message, int shouterLocation) {
    listeners.stream()
            .forEach(listener ->
                    helper(listener, shouterLocation, message));
  }

  public void broadcast1(String message, Person shouter) {
    int shouterLocation = shouter.getLocation();
    boolean shortEnough = (message.length() <= 180);
    deductCredits(shortEnough, message, shouter);
    for (Person listener : listeners) {
      boolean withinRange = (Math.abs(listener.getLocation() - shouterLocation) <= range);
      if (withinRange && (shortEnough || shouter.getCredits() >= 0)) {
        listener.hear(message);
      }
    }
  }

  private void deductCredits(boolean shortEnough, String message, Person shouter) {
    if (!shortEnough) {
      shouter.setCredits(shouter.getCredits() - 2);
    }
    Matcher matcher = BUY_PATTERN.matcher(message);
    if (matcher.find()) {
      shouter.setCredits(shouter.getCredits() - 5);
    }
  }

  private void helper(Person p, int distance, String msg) {
    boolean withinRange = (Math.abs(p.getLocation() - distance) <= range);
    boolean shortEnough = (msg.length() <= 180);
    if (withinRange && shortEnough) p.hear(msg);
  }
}
