package io.cucumber.shouty;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NetworkTest {

  private int range = 100;
  private Network network = new Network(range);
  private String message = "Free bagels";

  @Test
  public void broadcasts_a_message_to_a_listener_within_range() {
    int seanLocation = 0;
    Person lucy = mock(Person.class);
    network.subscribe(lucy);
    network.broadcast(message, seanLocation);

    verify(lucy).hear(message);
  }

  @Test
  public void does_not_broadcast_a_message_to_a_listener_out_of_range() {
    int sean_location = 0;
    Person laura = mock(Person.class);
    when(laura.getLocation()).thenReturn(101);
    network.subscribe(laura);
    network.broadcast(message, sean_location);

    verify(laura, never()).hear(message);
  }

  @Test
  public void does_not_broadcast_a_message_to_a_listener_out_of_range_negative_distance() {
    int sean_location = 101;
    Person zac = mock(Person.class);
    when(zac.getLocation()).thenReturn(0);
    network.subscribe(zac);
    network.broadcast(message, sean_location);

    verify(zac, never()).hear(message);
  }

  @Test
  public void does_not_broadcast_a_message_over_180_characters_even_if_listener_is_in_range() {
    int lysol_location = 0;

    char[] chars = new char[181];
    Arrays.fill(chars, 'x');
    String longMessage = String.valueOf(chars);

    Person dunkin = mock(Person.class);
    network.subscribe(dunkin);
    network.broadcast(longMessage, lysol_location);

    verify(dunkin, never()).hear(longMessage);
  }

  @Test
  public void deducts_2_credits_for_a_shout_over_180_characters() {
    char[] chars = new char[181];
    Arrays.fill(chars, 'x');
    String longMessage = String.valueOf(chars);

    Person sean = new Person("Sean", network, 0, 0);
    Person laura = new Person("laura", network, 0, 10);

    network.subscribe(laura);
    network.broadcast1(longMessage, sean);

    assertEquals(-2, sean.getCredits());
  }


  @Test
  public void deducts_5_credits_for_mentioning_the_word_buy() {
    String message = "Come buy these awesome croissant";

    Person sean = new Person("Sean", network, 0, 100);
    Person laura = new Person("laura", network, 0, 10);

    network.subscribe(laura);
    network.broadcast1(message, sean);

    assertEquals(95, sean.getCredits());
  }

  @Test
  public void deducts_5_credits_for_mentioning_the_word_buy_is_capitalized() {
    String message = "Come Buy these awesome croissant";

    Person sean = new Person("Sean", network, 0, 100);
    Person laura = new Person("laura", network, 0, 10);

    network.subscribe(laura);
    network.broadcast1(message, sean);

    assertEquals(95, sean.getCredits());
  }

  @Test
  public void deducts_5_credits_for_mentioning_the_word_buy_several_times() {
    String message = "Come buy buy buy these awesome croissant";

    Person sean = new Person("Sean", network, 0, 100);
    Person laura = new Person("laura", network, 0, 10);

    network.subscribe(laura);
    network.broadcast1(message, sean);

    assertEquals(95, sean.getCredits());
  }
}
