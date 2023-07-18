package io.cucumber.shouty.support;

import io.cucumber.java.en.When;
import io.cucumber.shouty.Person;

public class ShoutSteps {

  private final ShoutyWorld world;

  public ShoutSteps(ShoutyWorld world) {
    this.world = world;
  }

  @When("{person} shouts")
  public void person_shouts(Person person) {
    world.shout(person, "Hello, world!");
  }

  @When("{person} shouts {string}")
  public void person_shouts_message(Person person, String message) {
    world.shout(person, message);
  }

  @When("{person} shouts {int} messages containing the word {string}")
  public void person_shouts_a_message_containing_the_word(Person person, int count, String word) throws Throwable {
    for (int i = 0; i < count; ++i) {
      world.shout(person, "a message containing the word " + word);
    }
  }

  @When("{person} shouts the following message")
  public void person_shouts_the_following_message(Person person, String message) throws Throwable {
    world.shout(person, message);
  }

  @When("{person} shouts {int} over-long messages")
  public void person_shouts_over_some_long_message(Person person, int count) {
    String baseMessage = "A message from Sean that is 181 characters long ";
    String padding = "x";
    String overlongMessage = baseMessage + padding.repeat(181 - baseMessage.length());
    for (int i = 0; i < count; ++i) {
      world.shout(person, overlongMessage);
    }
  }

  //  @When("{person} shouts a long message")
//  public void person_shouts_a_long_message(Person person) throws Throwable {
//    String longMessage = String.join(
//            "\n",
//            "A message from sean",
//            "that spans multiple lines"
//    );
//    world.shout(person, longMessage);
//  }

//  @When("{person} shouts {string}")
//  public void person_shouts_message(Person person, String message) throws Throwable {
//    shout(person, message);
//    messageFromSean = message;
//  }
}
