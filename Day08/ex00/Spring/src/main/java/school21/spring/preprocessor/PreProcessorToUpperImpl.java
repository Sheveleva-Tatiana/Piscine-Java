package school21.spring.preprocessor;

import org.springframework.stereotype.Component;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String process(String str) {
        return str.toLowerCase();
    }
}
