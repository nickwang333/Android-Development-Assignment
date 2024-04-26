package edu.northeastern.numad24sp_zhuofengwang.linkActivity;

public class Link implements LinkClickListener {
    private final String url;
    private final String linkName;

    public Link(String url, String linkName) {
        this.url = url;
        this.linkName = linkName;
    }

    public String getUrl() {
        return url;
    }

    public String getLinkName(){
        return linkName;
    }

    @Override
    public void onLinkClick(int position) {

    }
}
