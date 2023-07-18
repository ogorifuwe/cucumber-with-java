package io.cucumber.shouty.support;

import io.cucumber.java.ParameterType;
import io.cucumber.shouty.Person;

public class ParameterTypes {
  private final ShoutyWorld world;

  public ParameterTypes(ShoutyWorld world) {
    this.world = world;
  }

  @ParameterType("Lucy|Sean|Larry")
  public Person person(String name) {
    if (world.people.containsKey(name))
      return world.people.get(name);

    Person person = new Person(name, world.network, 0, 100);
    world.people.put(name, person);
    return person;
  }
}
