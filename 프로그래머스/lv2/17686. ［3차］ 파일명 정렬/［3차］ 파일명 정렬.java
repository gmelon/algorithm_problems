import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import java.util.Map.*;

class Solution {
    public String[] solution(String[] files) {
       Pattern headPattern = Pattern.compile("[a-zA-Z-. ]+");
        Pattern numberPattern = Pattern.compile("[0-9]{1,5}");

        Map<Integer, String> map = IntStream.range(0, files.length)
            .boxed()
            .collect(Collectors.toMap(i -> i, i -> files[i]));

        return map.entrySet().stream()
            .sorted((Entry<Integer, String> e1, Entry<Integer, String> e2) -> {

                String f1 = e1.getValue();
                String f2 = e2.getValue();

                // HEAD 비교
                Matcher f1HeadMatcher = headPattern.matcher(f1);
                f1HeadMatcher.find();
                String f1Head = f1.substring(f1HeadMatcher.start(), f1HeadMatcher.end());
                Matcher f2HeadMatcher = headPattern.matcher(f2);
                f2HeadMatcher.find();
                String f2Head = f2.substring(f2HeadMatcher.start(), f2HeadMatcher.end());

                if (!f1Head.equalsIgnoreCase(f2Head)) {
                    return f1Head.toLowerCase().compareTo(f2Head.toLowerCase());
                }

                // NUMBER 비교
                Matcher f1NumberMatcher = numberPattern.matcher(f1);
                f1NumberMatcher.find();
                int f1Number = Integer.parseInt(f1.substring(f1NumberMatcher.start(), f1NumberMatcher.end()));
                Matcher f2NumberMatcher = numberPattern.matcher(f2);
                f2NumberMatcher.find();
                int f2Number = Integer.parseInt(f2.substring(f2NumberMatcher.start(), f2NumberMatcher.end()));

                if (f1Number != f2Number) {
                    return f1Number - f2Number;
                }

                return e1.getKey() - e2.getKey();
            })
            .map(Entry::getValue)
            .toArray(String[]::new);
    }
}