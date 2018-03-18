package govirtual360.numberverify.service.model;

public class Notification {
    private String id,timestamp,type,receiverId,receiverFCMToken,content;

    public Notification() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverFCMToken() {
        return receiverFCMToken;
    }

    public void setReceiverFCMToken(String receiverFCMToken) {
        this.receiverFCMToken = receiverFCMToken;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Notification(String id, String timestamp, String type, String receiverId, String receiverFCMToken, String content) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.receiverId = receiverId;
        this.receiverFCMToken = receiverFCMToken;
        this.content = content;
    }
}
