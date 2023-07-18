package io.cucumber.shouty;

import java.util.ArrayList;
import java.util.List;

public class Person {

  private final List<String> messagesHeard = new ArrayList<>();
  private final String name;
  private final Network network;
  private int location;
  private int credits;

  public Person(String name, Network network, int location, int credits) {
    this.name = name;
    this.network = network;
    this.location = location;
    this.credits = credits;
    network.subscribe(this);
  }

  public void shout(String message) {
    network.broadcast(message, 0);
  }

  public void hear(String message) {
      messagesHeard.add(message);
  }

  public void moveTo(int location) {
    this.setLocation(location);
  }

  public List<String> getMessagesHeard() {
    return messagesHeard;
  }

  public String getName() {
    return name;
  }

  public Network getNetwork() {
    return network;
  }

  public int getLocation() {
    return location;
  }

  private void setLocation(int location) {
    this.location = location;
  }

  public int getCredits() { return credits; }

  public void setCredits(int credits) {
    this.credits = credits;
  }
}
