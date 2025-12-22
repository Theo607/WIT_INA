package eu.jpereira.trainings.designpatterns.structural.decorator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator.MessageTruncator;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator.URLAppender;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator.WordCensor;

public class ChainCensorDecoratorTest {

    private DummyChannel dummy;
    private SocialChannel channel;

    @Before
    public void setUp() {
        dummy = new DummyChannel();

        channel = new WordCensor(
                    new MessageTruncator(
                      30,
                      new URLAppender("https://example.com", dummy) 
                    ),
                    "badword"
                 );
    }

    @Test
    public void testChainedDecorators() {
        String message = "badword";
        String expected = "***** https://example.com";

        channel.deliverMessage(message);

        assertEquals(expected, dummy.getLastMessage());
    }
  private static class DummyChannel implements SocialChannel {
      private String lastMessage;
  
      @Override
      public void deliverMessage(String message) {
          this.lastMessage = message;
      }
  
      public String getLastMessage() {
          return lastMessage;
      }
  }
} 
