package messageUtils;

import messageUtils.Message;

import java.util.Date;

public class MessageImpl implements Message {
    private final String nick;
    private final String content;
    private final Date time;

    public MessageImpl(String nick, String content, Date time) {
        this.nick = nick;
        this.content = content;
        this.time = time;
    }

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Date getTime() {
        return time;
    }
}
