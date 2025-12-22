package eu.jpereira.trainings.designpatterns.structural.decorator;

import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator.WordCensor;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class WordCensorTest {

    private DummyChannel dummy;
    private SocialChannel channel;

    @Before
    public void setUp() {
        dummy = new DummyChannel();
        channel = new WordCensor(dummy, "badword");
    }

    @Test
    public void testMessageIsCensored() {
        String message = "This is a badword in the message";
        channel.deliverMessage(message);

        assertEquals("This is a ***** in the message", dummy.getLastMessage());
    }

    private static class DummyChannel implements SocialChannel {
        private String lastMessage;

        @Override
        public void deliverMessage(String message) {
            lastMessage = message;
        }

        public String getLastMessage() {
            return lastMessage;
        }
    }
}
