package connectionUtils;

import messageUtils.Message;

public interface Connection {

    void send(Message message);

    void close();
}
