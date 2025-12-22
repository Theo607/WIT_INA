package eu.jpereira.trainings.designpatterns.structural.decorator.channel.decorator;

import java.util.regex.Pattern;
import eu.jpereira.trainings.designpatterns.structural.decorator.channel.SocialChannel;

/**
 * Decorator that censors a given word by replacing it with "*****".
 */
public class WordCensor extends SocialChannelDecorator {

    private final String wordToCensor;

    public WordCensor(SocialChannel delegate, String wordToCensor) {
        this.delegate = delegate;
        this.wordToCensor = wordToCensor;
    }

    @Override
    public void deliverMessage(String message) {
        String pattern = "(?i)\\b" + Pattern.quote(wordToCensor) + "\\b";
        String censored = message.replaceAll(pattern, "*****");

        delegate.deliverMessage(censored);
    }
}
