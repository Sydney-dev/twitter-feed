package org.sydney.twitter.domain;

public class Tweet {

    private String user;
    private String text;
    private Long priority;

    private Tweet(Builder builder) {
        setUser(builder.user);
        setText(builder.text);
        setPriority(builder.priority);
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public static final class Builder {
        private String user;
        private String text;
        private Long priority;

        private Builder() {
        }

        public Builder withUser(String user) {
            this.user = user;
            return this;
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public Builder withPriority(Long priority){
            this.priority = priority;
            return this;
        }

        public Tweet build() {
            return new Tweet(this);
        }
    }
}
