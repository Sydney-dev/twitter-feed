package org.sydney.twitter;

public class Tweet {

    private String user;
    private String text;

    private Tweet(Builder builder) {
        setUser(builder.user);
        setText(builder.text);
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

    public static final class Builder {
        private String user;
        private String text;

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


        public Tweet build() {
            return new Tweet(this);
        }

    }
}
