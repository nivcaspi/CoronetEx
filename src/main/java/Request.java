import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
class Request implements Serializable {

    Action action;
    String key;
    List<String> value;
    String add;
    String pattern;
}
