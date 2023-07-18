package io.cucumber.shouty;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.shouty.support.ShoutyWorld;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {

  private final ShoutyWorld world;

  public StepDefinitions(ShoutyWorld world) {
    this.world = world;
  }

  private String messageFromSean;

  static class Whereabouts {
    public String name;
    public Integer location;

    public Whereabouts(String name, int location) {
      this.name = name;
      this.location = location;
    }
  }

  @DataTableType
  public Whereabouts defineWhereabouts(Map<String, String> entry) {
    return new Whereabouts(entry.get("name"), Integer.parseInt(entry.get("location")));
  }

  @Before
  public void createNetwork() {
  }

  @Given("the range is {int}")
  public void the_range_is(int range) throws Throwable {
    world.network = new Network(range);
  }

  @Given("a person named {word}")
  public void a_person_named(String name) throws Throwable {
    world.people.put(name, new Person(name, world.network, 0, 100));
  }

  @Given("{person} is located at {int}")
  public void person_is_located_at(Person person, int location) throws Throwable {
    person.moveTo(location);
  }

  @Given("{person} has bought {int} credits")
  public void person_has_bought_credits(Person person, int credits) {
    person.setCredits(credits);
  }

  @Given("people are located at")
  public void people_are_located_at(@Transpose List<Whereabouts> whereabouts) {

      whereabouts.forEach(wa ->
                    world.people.put(wa.name, new Person(wa.name, world.network, wa.location, 100)));
  }



  @Then("Lucy should hear Sean's message")
  public void lucy_hears_sean_s_message() throws Throwable {
    assertEquals((Collections.singletonList(messageFromSean)),
//            assertEquals(Arrays.asList("Hello, world!"),
            world.people.get("Lucy").getMessagesHeard());
  }

  @Then("Lucy should hear a shout")
  public void lucy_should_hear_a_shout() {
    assertEquals(1,
            world.people.get("Lucy").getMessagesHeard().size());
  }

  @Then("{person} should not hear a shout")
  public void larry_should_not_hear_a_shout(String name) {
    assertEquals(0,
            world.people.get(name).getMessagesHeard().size());
  }

  @Then("{word} should not hear Sean's message")
  public void larry_should_not_hear_sean_s_message(String name) {
    List<String> heardByLarry = world.people.get(name).getMessagesHeard();
    assertThat(heardByLarry, not(hasItem(messageFromSean)));
  }

  @Then("Lucy hears the following messages:")
  public void lucy_hears_the_following_messages(DataTable expectedMessages) throws Throwable{
    List<List<String>> actualMessages = new ArrayList<>();
    List<String> heard = world.people.get("Lucy").getMessagesHeard();
    heard.forEach(msg -> actualMessages.add(Collections.singletonList(msg)));
    expectedMessages.diff(DataTable.create(actualMessages));
  }

}