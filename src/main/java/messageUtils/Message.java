package messageUtils;

import java.io.Serializable;
import java.util.Date;

public interface Message extends Serializable {

    String getNick();

    String getContent();

    Date getTime();
}
