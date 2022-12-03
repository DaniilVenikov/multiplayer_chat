package connectionUtils;

import messageUtils.Message;

public interface ConnectionListener {

    void connectionCreated(Connection c);

    void connectionClosed(Connection c);

    void connectionException(Connection c, Exception e);

    void sendMessage(Message message);

}
